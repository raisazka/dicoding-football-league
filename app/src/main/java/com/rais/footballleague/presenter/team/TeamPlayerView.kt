package com.rais.footballleague.presenter.team

import com.rais.footballleague.model.Player
import com.rais.footballleague.presenter.base.BaseView

interface TeamPlayerView : BaseView {
    fun getTeamPlayers(players: List<Player>)
}