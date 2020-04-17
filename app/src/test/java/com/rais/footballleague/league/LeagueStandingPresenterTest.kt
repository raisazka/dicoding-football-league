package com.rais.footballleague.league

import com.google.gson.Gson
import com.rais.footballleague.TestContextProvider
import com.rais.footballleague.model.TeamStanding
import com.rais.footballleague.model.responses.StandingResponse
import com.rais.footballleague.network.ApiRepository
import com.rais.footballleague.presenter.league.LeagueStandingPresenter
import com.rais.footballleague.presenter.league.LeagueStandingView
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class LeagueStandingPresenterTest {

    @Mock
    private lateinit var view: LeagueStandingView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse : Deferred<String>

    private lateinit var presenter : LeagueStandingPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = LeagueStandingPresenter(view, apiRepository, gson,
            TestContextProvider()
        )
    }

    @Test
    fun testGetLeagueStanding() {
        val listStanding : List<TeamStanding> = mutableListOf()
        val response = StandingResponse(listStanding)
        val leagueId = 4328

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", StandingResponse::class.java)).thenReturn(response)

            presenter.getTeamStanding(leagueId)
            Mockito.verify(view).showLoading()
            Mockito.verify(view).getTeamStanding(listStanding)
            Mockito.verify(view).hideLoading()
        }
    }

}