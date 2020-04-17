package com.rais.footballleague.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.rais.footballleague.R
import com.rais.footballleague.model.Team
import com.rais.footballleague.network.ApiRepository
import com.rais.footballleague.presenter.team.TeamDetailPresenter
import com.rais.footballleague.presenter.team.TeamDetailView
import com.rais.footballleague.utils.invisible
import com.rais.footballleague.utils.visible
import kotlinx.android.synthetic.main.fragment_description.*

class TeamDescriptionFragment : Fragment(), TeamDetailView {

    private val teamDesc : MutableList<Team> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_description, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val gson = Gson()
        val apiRepository = ApiRepository()
        val presenter = TeamDetailPresenter(this, apiRepository, gson)

        val teamId = activity!!.intent.getIntExtra("teamId", 0)

        presenter.getTeamDetail(teamId)
    }

    override fun getTeamDetail(list: List<Team>) {
        teamDesc.clear()
        teamDesc.addAll(list)
        teamDesc.map {
            desc_content.text = it.teamDescription
            stadium_content.text = it.teamStadium
        }
    }

    override fun showLoading() {
        prg_desc.visible()
        prg_desc_std.visible()
    }

    override fun hideLoading() {
        prg_desc.invisible()
        prg_desc_std.invisible()
    }
}
