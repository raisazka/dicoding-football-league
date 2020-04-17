package com.rais.footballleague.presenter.match

import com.rais.footballleague.model.Match
import com.rais.footballleague.presenter.base.BaseView

interface SearchMatchView : BaseView {
    fun searchTeam(matches: List<Match>)
    fun hideText()
    fun showText()
}