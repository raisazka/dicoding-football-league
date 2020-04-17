package com.rais.footballleague.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.rais.footballleague.R
import com.rais.footballleague.model.Player
import com.rais.footballleague.network.ApiRepository
import com.rais.footballleague.presenter.team.TeamPlayerPresenter
import com.rais.footballleague.presenter.team.TeamPlayerView
import com.rais.footballleague.ui.activities.PlayerDetailActivity
import com.rais.footballleague.ui.adapter.list.PlayerAdapter
import com.rais.footballleague.utils.invisible
import com.rais.footballleague.utils.visible
import kotlinx.android.synthetic.main.fragment_team_player.*
import org.jetbrains.anko.support.v4.startActivity

class TeamPlayerFragment : Fragment(), TeamPlayerView {

    private val listPlayer : MutableList<Player> = mutableListOf()

    lateinit var adapter : PlayerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_team_player, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val gson = Gson()
        val apiRepository = ApiRepository()
        val presenter = TeamPlayerPresenter(this, apiRepository, gson)

        val teamId = activity!!.intent.getIntExtra("teamId", 0)

        presenter.getTeamPlayers(teamId)
        adapter = PlayerAdapter(
            context!!,
            listPlayer
        ) {
            startActivity<PlayerDetailActivity>("playerId" to it.playerId)
        }

        rv_team_player.layoutManager = LinearLayoutManager(context)
        rv_team_player.adapter = adapter
    }

    override fun getTeamPlayers(players: List<Player>) {
        listPlayer.clear()
        listPlayer.addAll(players)
        adapter.notifyDataSetChanged()
    }

    override fun showLoading() {
        prg_players.visible()
    }

    override fun hideLoading() {
        prg_players.invisible()
    }
}
