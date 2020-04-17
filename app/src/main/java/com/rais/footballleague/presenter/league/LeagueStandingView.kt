package com.rais.footballleague.presenter.league

import com.rais.footballleague.model.TeamStanding
import com.rais.footballleague.presenter.base.BaseView

interface LeagueStandingView : BaseView {
    fun getTeamStanding(teams: List<TeamStanding>)
}