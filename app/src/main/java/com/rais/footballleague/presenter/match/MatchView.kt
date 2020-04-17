package com.rais.footballleague.presenter.match

import com.rais.footballleague.model.Match
import com.rais.footballleague.presenter.base.BaseView

interface MatchView : BaseView {
    fun getMatches(matches: List<Match>)
}