package com.rais.footballleague.presenter.team

import com.google.gson.Gson
import com.rais.footballleague.model.responses.TeamResponse
import com.rais.footballleague.network.ApiRepository
import com.rais.footballleague.network.SportDBApi
import com.rais.footballleague.utils.CoroutineContextProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchTeamPresenter(private val view: SearchTeamView,
                          private val apiRepository: ApiRepository, private val gson: Gson,
                          private val context : CoroutineContextProvider = CoroutineContextProvider()
) {


    fun searchTeams(teamName: String?) {
        view.showLoading()
        GlobalScope.launch(context.main) {
            val data = gson.fromJson(apiRepository.doRequest(SportDBApi.searchTeams(teamName)).await(),
                                    TeamResponse::class.java).teams?.filter {
                it.sport == "Soccer"
            }

            if (data == null) {
                view.showMessage()
            } else {
                view.hideMessage()
                view.searchTeams(data)
            }
            view.hideLoading()
        }
    }

}