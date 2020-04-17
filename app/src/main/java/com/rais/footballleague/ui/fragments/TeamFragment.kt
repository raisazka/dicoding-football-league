package com.rais.footballleague.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.rais.footballleague.R
import com.rais.footballleague.model.Team
import com.rais.footballleague.network.ApiRepository
import com.rais.footballleague.presenter.team.TeamsPresenter
import com.rais.footballleague.presenter.team.TeamsView
import com.rais.footballleague.ui.activities.TeamDetailActivity
import com.rais.footballleague.ui.adapter.list.TeamAdapter
import com.rais.footballleague.utils.EspressoIdlingResource
import com.rais.footballleague.utils.invisible
import com.rais.footballleague.utils.visible
import kotlinx.android.synthetic.main.fragment_team.*
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.startActivity

class TeamFragment : Fragment(), TeamsView {

    private lateinit var adapter : TeamAdapter

    private val listTeam : MutableList<Team> = mutableListOf()

    private lateinit var presenter : TeamsPresenter

    private lateinit var progressBar : ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref: SharedPreferences = this.activity!!.getSharedPreferences("prefs", 0)
        progressBar = find(R.id.prg_teams)
        val leagueId = sharedPref.getInt("leagueIdPrefs", 0)
        val apiRepository = ApiRepository()
        val gson = Gson()
        presenter = TeamsPresenter(this, apiRepository, gson)
        adapter = TeamAdapter(
            context!!,
            listTeam
        ) {
            startActivity<TeamDetailActivity>("teamId" to it.teamId)
        }
        EspressoIdlingResource.increment()
        presenter.getTeams(leagueId)
        rv_team.layoutManager = LinearLayoutManager(this.activity)
        rv_team.adapter = adapter
    }

    override fun showTeams(teams: List<Team>) {
        if(!EspressoIdlingResource.idlingresource.isIdleNow) {
            EspressoIdlingResource.decrement()
        }
        listTeam.clear()
        listTeam.addAll(teams)
        adapter.notifyDataSetChanged()
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }
}
