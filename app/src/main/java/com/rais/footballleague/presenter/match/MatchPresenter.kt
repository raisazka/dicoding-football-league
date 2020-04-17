package com.rais.footballleague.presenter.match

import com.google.gson.Gson
import com.rais.footballleague.model.responses.MatchResponse
import com.rais.footballleague.network.ApiRepository
import com.rais.footballleague.network.SportDBApi
import com.rais.footballleague.utils.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MatchPresenter(private val view: MatchView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()) {


    fun getPastMatches(leagueId: Int) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequest(SportDBApi.getPastMatch(leagueId)).await(),
            MatchResponse::class.java)
                view.hideLoading()
                view.getMatches(data.events)
        }
    }

    fun getFutureMatches(leagueId: Int) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequest(SportDBApi.getFutureMatch(leagueId)).await(),
                MatchResponse::class.java)

                view.hideLoading()
                view.getMatches(data.events)
        }
    }

}