package com.rais.footballleague.ui.activities

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.gson.Gson
import com.rais.footballleague.R
import com.rais.footballleague.helper.MatchDatabaseOpenHelper
import com.rais.footballleague.model.FavoriteMatch
import com.rais.footballleague.model.Match
import com.rais.footballleague.model.Team
import com.rais.footballleague.network.ApiRepository
import com.rais.footballleague.presenter.match.MatchDetailPresenter
import com.rais.footballleague.presenter.match.MatchDetailView
import com.rais.footballleague.utils.invisible
import com.rais.footballleague.utils.visible
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_match_detail.*
import kotlinx.android.synthetic.main.match_detail.*
import org.jetbrains.anko.contentView
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar

class MatchDetailActivity : AppCompatActivity(),
    MatchDetailView {

    private lateinit var presenter : MatchDetailPresenter

    private var menuItem : Menu? = null
    private var isFavorite = false

    private var matchId : Int = 0

    private lateinit var theMatch : Match

    private lateinit var db : MatchDatabaseOpenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match_detail)

        db = MatchDatabaseOpenHelper.getInstance(this)

        supportActionBar?.title = "Match Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        matchId = intent.getIntExtra("matchId", 0)

        favoriteStatus()

        val apiRepository = ApiRepository()
        val gson = Gson()
        presenter = MatchDetailPresenter(
            this,
            apiRepository,
            gson
        )
        presenter.getEventDetail(matchId)
    }

    override fun getMatchDetail(matches: List<Match>) {
        matches.map {match ->
            theMatch = match
        }
        home_name.text = theMatch.homeTeamName
        away_name.text = theMatch.awayTeamName
        if(theMatch.scoreHome == null || theMatch.scoreAway == null) {
            home_score.text = "0"
            away_score.text = "0"
        } else {
            home_score.text = theMatch.scoreHome.toString()
            away_score.text = theMatch.scoreAway.toString()
        }
        goal_home.text = theMatch.homeGoals
        goal_away.text = theMatch.awayGoals
        match_date.text = theMatch.date
        match_detail_name.text = theMatch.name
        home_yellow.text = theMatch.homeYellow
        away_yellow.text = theMatch.awayYellow
        home_red.text = theMatch.homeRed
        away_red.text = theMatch.awayRed
        home_goalie.text = theMatch.homeGoalie
        away_goalie.text = theMatch.awayGoalie
        home_mid.text = theMatch.homeMid
        away_mid.text = theMatch.awayMid
        home_fwd.text = theMatch.homeForward
        away_fwd.text = theMatch.awayForward
        presenter.getHomeTeamData(theMatch.homeTeamId!!)
        presenter.getAwayTeamData(theMatch.awayTeamId!!)
        presenter.getAwayTeamData(theMatch.awayTeamId!!)
    }

    override fun getHomeTeam(homeTeam: List<Team>) {
        Picasso.get().load(homeTeam[0].teamBadge).fit().into(logo_home)
    }

    override fun getAwayTeam(awayTeam: List<Team>) {
        Picasso.get().load(awayTeam[0].teamBadge).fit().into(logo_away)
    }

    override fun showLoading() {
        prg_detail_match.visible()
    }

    override fun hideLoading() {
        prg_detail_match.invisible()
    }

    private fun favoriteStatus() {
        db.use {
            val result = select(FavoriteMatch.TABLE_FAVORITE).whereArgs("(MATCH_ID = {id})", "id" to matchId.toString())
            val favorite = result.parseList(classParser<FavoriteMatch>())
            if (favorite.isNotEmpty()) isFavorite = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }

            R.id.add_to_favorite -> {
                if (isFavorite) deleteFromFavorite() else addToFavorite()
                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun setFavorite() {
        if(isFavorite) {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_fav)
        } else {
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_add)
        }
    }

    private fun addToFavorite() {
        try {
            db.use {
                insert(FavoriteMatch.TABLE_FAVORITE, FavoriteMatch.MATCH_ID to theMatch.matchId,
                    FavoriteMatch.HOME_NAME to theMatch.homeTeamName, FavoriteMatch.AWAY_NAME to theMatch.awayTeamName,
                    FavoriteMatch.MATCH_NAME to theMatch.name, FavoriteMatch.HOME_SCORE to theMatch.scoreHome,
                    FavoriteMatch.AWAY_SCORE to theMatch.scoreAway, FavoriteMatch.MATCH_DATE to theMatch.date)
            }
            contentView?.snackbar("Added To Favorite")?.show()
        } catch (e: SQLiteConstraintException) {
            contentView?.snackbar(e.localizedMessage)?.show()
        }
    }

    private fun deleteFromFavorite() {
        try {
            db.use {
                delete(FavoriteMatch.TABLE_FAVORITE, "(MATCH_ID = {id})", "id" to matchId.toString())
            }
            contentView?.snackbar("Deleted From Favorite")?.show()
        } catch (e: SQLiteConstraintException) {
            contentView?.snackbar(e.localizedMessage)?.show()
        }
    }
}