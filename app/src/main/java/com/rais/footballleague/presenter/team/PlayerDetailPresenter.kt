package com.rais.footballleague.presenter.team

import com.google.gson.Gson
import com.rais.footballleague.model.responses.PlayerResponse
import com.rais.footballleague.network.ApiRepository
import com.rais.footballleague.network.SportDBApi
import com.rais.footballleague.utils.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlayerDetailPresenter(private val view: PlayerDetailView,
                            private val gson: Gson, private val apiRepository: ApiRepository,
                            private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getPlayerDetails(playerId: Int) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequest(SportDBApi.getPlayerDetail(playerId)).await(),
                PlayerResponse::class.java)
            view.getPlayerDetail(data.players)
            view.hideLoading()
        }
    }


}