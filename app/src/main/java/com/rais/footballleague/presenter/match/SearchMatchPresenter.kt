package com.rais.footballleague.presenter.match

import com.google.gson.Gson
import com.rais.footballleague.model.responses.SearchMatchResponse
import com.rais.footballleague.network.ApiRepository
import com.rais.footballleague.network.SportDBApi
import com.rais.footballleague.utils.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchMatchPresenter(
    private val view: SearchMatchView,
    private val apiRepository: ApiRepository,
    private val gson: Gson, private val context: CoroutineContextProvider = CoroutineContextProvider()
) {


    fun getMatch(matchName: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(
                apiRepository.doRequest(SportDBApi.searchEvent(matchName)).await(), SearchMatchResponse::class.java).event?.filter {
                it.sport == "Soccer"
            }

                if (data == null) {
                    view.showText()
                } else {
                    view.hideText()
                    view.searchTeam(data)
                }
            view.hideLoading()
        }
    }

}