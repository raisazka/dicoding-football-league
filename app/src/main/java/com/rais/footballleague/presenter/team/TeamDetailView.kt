package com.rais.footballleague.presenter.team

import com.rais.footballleague.model.Team
import com.rais.footballleague.presenter.base.BaseView

interface TeamDetailView : BaseView {
    fun getTeamDetail(list: List<Team>)
}