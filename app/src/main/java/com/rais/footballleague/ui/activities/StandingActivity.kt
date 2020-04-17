package com.rais.footballleague.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.rais.footballleague.R
import com.rais.footballleague.model.TeamStanding
import com.rais.footballleague.network.ApiRepository
import com.rais.footballleague.presenter.league.LeagueStandingPresenter
import com.rais.footballleague.presenter.league.LeagueStandingView
import com.rais.footballleague.ui.adapter.list.StandingAdapter
import com.rais.footballleague.utils.EspressoIdlingResource
import com.rais.footballleague.utils.invisible
import com.rais.footballleague.utils.visible
import kotlinx.android.synthetic.main.activity_standing.*

class StandingActivity : AppCompatActivity(), LeagueStandingView {

    private var listStanding : MutableList<TeamStanding> = mutableListOf()

    lateinit var adapter : StandingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_standing)
        val apiRepository = ApiRepository()
        val gson = Gson()
        val presenter = LeagueStandingPresenter(this, apiRepository, gson)
        val leagueId = intent.getIntExtra("leagueId", 0)
        Log.d("StandingActivity", "LeagueID:${leagueId}")
        EspressoIdlingResource.increment()
        presenter.getTeamStanding(leagueId)
        adapter = StandingAdapter(this, listStanding)
        rv_standing.adapter = adapter
        rv_standing.layoutManager = LinearLayoutManager(this)
    }

    override fun getTeamStanding(teams: List<TeamStanding>) {
        if(!EspressoIdlingResource.idlingresource.isIdleNow) {
            EspressoIdlingResource.decrement()
        }
        listStanding.clear()
        listStanding.addAll(teams)
        adapter.notifyDataSetChanged()
    }

    override fun showLoading() {
        prf_standing.visible()
    }

    override fun hideLoading() {
        prf_standing.invisible()

    }
}
