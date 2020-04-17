package com.rais.footballleague.presenter.team

import com.rais.footballleague.model.Team
import com.rais.footballleague.presenter.base.BaseView

interface SearchTeamView : BaseView {
    fun searchTeams(teams: List<Team>)
    fun showMessage()
    fun hideMessage()
}