package com.rais.footballleague.presenter.team

import com.rais.footballleague.model.Team
import com.rais.footballleague.presenter.base.BaseView

interface TeamsView : BaseView {
    fun showTeams(teams: List<Team>)
}