package com.rais.footballleague.presenter.match

import com.rais.footballleague.model.Match
import com.rais.footballleague.model.Team
import com.rais.footballleague.presenter.base.BaseView

interface MatchDetailView : BaseView{
    fun getMatchDetail(matches : List<Match>)
    fun getHomeTeam(homeTeam: List<Team>)
    fun getAwayTeam(awayTeam: List<Team>)
}