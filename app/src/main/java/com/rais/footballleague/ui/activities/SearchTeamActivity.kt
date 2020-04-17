package com.rais.footballleague.ui.activities

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.rais.footballleague.R
import com.rais.footballleague.model.Team
import com.rais.footballleague.network.ApiRepository
import com.rais.footballleague.presenter.team.SearchTeamPresenter
import com.rais.footballleague.presenter.team.SearchTeamView
import com.rais.footballleague.ui.adapter.list.TeamAdapter
import com.rais.footballleague.utils.EspressoIdlingResource
import com.rais.footballleague.utils.invisible
import com.rais.footballleague.utils.visible
import kotlinx.android.synthetic.main.activity_search_team.*
import org.jetbrains.anko.startActivity

class SearchTeamActivity : AppCompatActivity(), SearchTeamView {

    private val teamList = mutableListOf<Team>()

    private lateinit var adapter: TeamAdapter

    lateinit var presenter: SearchTeamPresenter

    private val gson = Gson()

    private val apiRepository = ApiRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_team)
        adapter = TeamAdapter(this, teamList) {
            startActivity<TeamDetailActivity>("teamId" to it.teamId)
        }

        val presenter = SearchTeamPresenter(this, apiRepository, gson)

        rv_search_team.layoutManager = LinearLayoutManager(this)
        rv_search_team.adapter = adapter
        EspressoIdlingResource.increment()
        presenter.searchTeams("")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_bar, menu)
        val searchMenu = menu!!.findItem(R.id.search_action)
        val searchView  = searchMenu.actionView as SearchView
        searchView.queryHint = "Search Team"
        val presenter = SearchTeamPresenter(this, apiRepository, gson)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                presenter.searchTeams(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                EspressoIdlingResource.increment()
                presenter.searchTeams(newText)
                return false
            }
        })
        return true
    }

    override fun searchTeams(teams: List<Team>) {
        if(!EspressoIdlingResource.idlingresource.isIdleNow) {
            EspressoIdlingResource.decrement()
        }
        teamList.clear()
        teamList.addAll(teams)
        adapter.notifyDataSetChanged()
    }

    override fun showMessage() {
        teamList.clear()
        no_team_found.visible()
    }

    override fun hideMessage() {
        no_team_found.invisible()
    }

    override fun showLoading() {
        teamList.clear()
        prg_search_team.visible()
    }

    override fun hideLoading() {
        prg_search_team.invisible()
    }
}
