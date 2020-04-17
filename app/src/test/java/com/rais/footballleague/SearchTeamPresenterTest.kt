package com.rais.footballleague

import com.google.gson.Gson
import com.rais.footballleague.model.Team
import com.rais.footballleague.model.responses.TeamResponse
import com.rais.footballleague.network.ApiRepository
import com.rais.footballleague.presenter.team.SearchTeamPresenter
import com.rais.footballleague.presenter.team.SearchTeamView
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchTeamPresenterTest {

    @Mock
    private lateinit var view: SearchTeamView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse : Deferred<String>

    private lateinit var presenter : SearchTeamPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = SearchTeamPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun searchTeamTest() {
        val listTeam : MutableList<Team> = mutableListOf()
        val response = TeamResponse(listTeam)
        val teamName = "Barcelona"

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", TeamResponse::class.java)).thenReturn(response)

            presenter.searchTeams(teamName)
            Mockito.verify(view).showLoading()
            Mockito.verify(view).searchTeams(listTeam)
            Mockito.verify(view).hideLoading()
        }
    }

}