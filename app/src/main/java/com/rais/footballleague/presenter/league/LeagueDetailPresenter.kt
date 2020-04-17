package com.rais.footballleague.presenter.league

import com.google.gson.Gson
import com.rais.footballleague.model.responses.LeagueResponse
import com.rais.footballleague.network.ApiRepository
import com.rais.footballleague.network.SportDBApi
import com.rais.footballleague.utils.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LeagueDetailPresenter(private val view: LeagueDetailView,
                            private val apiRepository: ApiRepository,
                            private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getLeagueDetails(leagueId: Int) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequest(SportDBApi.getLeagueDetail(leagueId)).await(),
                                     LeagueResponse::class.java)
                view.getLeagueDetails(data.leagues)
               view.hideLoading()
        }
    }

}