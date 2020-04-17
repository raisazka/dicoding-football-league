package com.rais.footballleague.presenter.league

import com.google.gson.Gson
import com.rais.footballleague.model.responses.StandingResponse
import com.rais.footballleague.network.ApiRepository
import com.rais.footballleague.network.SportDBApi
import com.rais.footballleague.utils.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LeagueStandingPresenter(private val view: LeagueStandingView,
                              private val apiRepository: ApiRepository,
                              private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getTeamStanding(leagueId: Int) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequest(SportDBApi.getTeamStanding(leagueId)).await(),
                StandingResponse::class.java)

            view.getTeamStanding(data.table)
            view.hideLoading()
        }
    }

}