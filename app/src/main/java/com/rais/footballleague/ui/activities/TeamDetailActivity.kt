package com.rais.footballleague.ui.activities

import android.app.PendingIntent
import android.database.sqlite.SQLiteConstraintException
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.rais.footballleague.R
import com.rais.footballleague.helper.TeamDatabaseOpenHelper
import com.rais.footballleague.model.FavoriteTeam
import com.rais.footballleague.model.Team
import com.rais.footballleague.network.ApiRepository
import com.rais.footballleague.presenter.team.TeamDetailPresenter
import com.rais.footballleague.presenter.team.TeamDetailView
import com.rais.footballleague.ui.adapter.favorite.TeamViewPagerAdapter
import com.rais.footballleague.utils.CustomTabHelpers
import com.rais.footballleague.utils.EspressoIdlingResource
import com.rais.footballleague.utils.invisible
import com.rais.footballleague.utils.visible
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_team_detail.*
import org.jetbrains.anko.contentView
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.toast

class TeamDetailActivity : AppCompatActivity(), TeamDetailView {

    private val teamDesc : MutableList<Team> = mutableListOf()

    private var customTabHelper: CustomTabHelpers = CustomTabHelpers()

    private var menuItem : Menu? = null

    private lateinit var db : TeamDatabaseOpenHelper

    private var teamId : Int = 0

    private var isFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_detail)
        view_pager_team.adapter =
            TeamViewPagerAdapter(
                supportFragmentManager
            )
        tabs_team.setupWithViewPager(view_pager_team)
        db = TeamDatabaseOpenHelper(this)
        val gson = Gson()
        val apiRepository = ApiRepository()
        val presenter = TeamDetailPresenter(this, apiRepository, gson)
        teamId = intent.getIntExtra("teamId", 0)
        EspressoIdlingResource.increment()
        presenter.getTeamDetail(teamId)

        logo_ig.setOnClickListener {
            initIntent("http://${teamDesc[0].teamInstagram}")
        }

        logo_fb.setOnClickListener {
            initIntent("http://${teamDesc[0].teamFacebook}")
        }

        logo_twitter.setOnClickListener {
            initIntent("http://${teamDesc[0].teamTwitter}")
        }

        logo_yt.setOnClickListener {
            initIntent("http://${teamDesc[0].teamYoutube}")
        }

        favoriteStatus()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun initIntent(url : String) {
        val builder = CustomTabsIntent.Builder()
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
        builder.addDefaultShareMenuItem()

        val anotherCustomTab = CustomTabsIntent.Builder().build()

        val requestCode = 100
        val intent = anotherCustomTab.intent
        intent.setData(Uri.parse(url))

        val pendingIntent = PendingIntent.getActivity(this,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT)

        builder.addMenuItem("Item", pendingIntent)
        builder.setShowTitle(true)

        val customTabsIntent = builder.build()

        val packageName = customTabHelper.getPackageNameToUse(this, url)

        if (packageName == null) {
            toast("You must have chrome to open this")
        } else {
            customTabsIntent.intent.setPackage(packageName)
            customTabsIntent.launchUrl(this, Uri.parse(url))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    private fun favoriteStatus() {
        db.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE).whereArgs("(TEAM_ID = {id})", "id" to teamId)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            Log.d("TeamDetailActivity", "FavTeam: ${favorite}")
            if(favorite.isNotEmpty()) isFavorite = true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if(isFavorite) deleteFavorite() else addFavorite()
                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun deleteFavorite() {
        try {
            db.use {
                delete(FavoriteTeam.TABLE_FAVORITE, "(TEAM_ID={id})", "id" to teamId)
            }
            contentView?.snackbar("Success Delete Team From Favorite")
        } catch (e: SQLiteConstraintException) {
            contentView?.snackbar(e.localizedMessage)
        }
    }

    private fun addFavorite() {
        try {
            db.use {
                insert(FavoriteTeam.TABLE_FAVORITE, FavoriteTeam.TEAM_NAME to teamDesc[0].teamName,
                    FavoriteTeam.TEAM_ID to teamDesc[0].teamId,
                    FavoriteTeam.TEAM_BADGE to teamDesc[0].teamBadge)
            }
            contentView?.snackbar("Success Add Team to Favorite!")
        } catch (e: SQLiteConstraintException) {
            contentView?.snackbar(e.localizedMessage)
        }
    }

    private fun setFavorite() {
        if(isFavorite) {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_fav)
        } else {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_add)
        }
    }

    override fun getTeamDetail(list: List<Team>) {
        if (EspressoIdlingResource.idlingresource.isIdleNow) {
            EspressoIdlingResource.decrement()
        }
        teamDesc.clear()
        teamDesc.addAll(list)
        teamDesc.map {
            Picasso.get().load(it.teamBadge).fit().into(team_logo)
            team_name.text = it.teamName
        }
    }

    override fun showLoading() {
        layout_team_detail.invisible()
        prg_team_detail.visible()
    }

    override fun hideLoading() {
        prg_team_detail.invisible()
        layout_team_detail.visible()
    }
}
