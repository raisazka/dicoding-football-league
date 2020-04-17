package com.rais.footballleague.presenter.team

import com.google.gson.Gson
import com.rais.footballleague.model.responses.TeamResponse
import com.rais.footballleague.network.ApiRepository
import com.rais.footballleague.network.SportDBApi
import com.rais.footballleague.utils.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamDetailPresenter(private val view: TeamDetailView,
                          private val apiRepository: ApiRepository, private val gson: Gson,
                          private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamDetail(teamId : Int) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequest(SportDBApi.getTeamDetail(teamId)).await(),
                TeamResponse::class.java)

            data.teams?.let { view.getTeamDetail(it) }
            view.hideLoading()
        }
    }

}