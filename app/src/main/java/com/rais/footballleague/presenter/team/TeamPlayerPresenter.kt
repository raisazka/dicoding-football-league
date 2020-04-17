package com.rais.footballleague.presenter.team

import com.google.gson.Gson
import com.rais.footballleague.model.responses.PlayerResponse
import com.rais.footballleague.network.ApiRepository
import com.rais.footballleague.network.SportDBApi
import com.rais.footballleague.utils.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamPlayerPresenter(private val view: TeamPlayerView,
                          private val apiRepository: ApiRepository,
                          private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()
) {

    fun getTeamPlayers(teamId : Int) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequest(SportDBApi.getTeamPlayers(teamId)).await(),
                PlayerResponse::class.java)
            view.getTeamPlayers(data.player)
            view.hideLoading()
        }
    }

}