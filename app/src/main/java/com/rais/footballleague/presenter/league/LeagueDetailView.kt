package com.rais.footballleague.presenter.league

import com.rais.footballleague.model.League
import com.rais.footballleague.presenter.base.BaseView

interface LeagueDetailView : BaseView {
    fun getLeagueDetails(leagueList: List<League>)
}