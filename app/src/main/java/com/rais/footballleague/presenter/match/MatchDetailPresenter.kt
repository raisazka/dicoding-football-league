package com.rais.footballleague.presenter.match

import com.google.gson.Gson
import com.rais.footballleague.model.responses.MatchResponse
import com.rais.footballleague.model.responses.TeamResponse
import com.rais.footballleague.network.ApiRepository
import com.rais.footballleague.network.SportDBApi
import com.rais.footballleague.utils.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MatchDetailPresenter(private val view: MatchDetailView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()
) {


    fun getEventDetail(eventId: Int) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequest(SportDBApi.getEventDetail(eventId)).await(), MatchResponse::class.java)

            view.hideLoading()
            view.getMatchDetail(data.events)
        }
    }


    fun getHomeTeamData(teamId : Int) {
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequest(SportDBApi.getTeamDetail(teamId)).await(), TeamResponse::class.java)
                view.getHomeTeam(data.teams!!)
        }
    }

    fun getAwayTeamData(teamId : Int) {
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequest(SportDBApi.getTeamDetail(teamId)).await(), TeamResponse::class.java)
                view.getAwayTeam(data.teams!!)
        }
    }

}