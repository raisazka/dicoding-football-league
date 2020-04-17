package com.rais.footballleague.league

import com.google.gson.Gson
import com.rais.footballleague.TestContextProvider
import com.rais.footballleague.model.League
import com.rais.footballleague.model.responses.LeagueResponse
import com.rais.footballleague.network.ApiRepository
import com.rais.footballleague.presenter.league.LeagueDetailPresenter
import com.rais.footballleague.presenter.league.LeagueDetailView
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class LeagueDetailPresenterTest {

    @Mock
    private lateinit var view: LeagueDetailView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse : Deferred<String>

    private lateinit var presenter : LeagueDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = LeagueDetailPresenter(view, apiRepository, gson,
            TestContextProvider()
        )
    }

    @Test
    fun getLeagueDetailTest() {
        val leagues : MutableList<League> = mutableListOf()
        val response = LeagueResponse(leagues)
        val leagueId = 4328

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", LeagueResponse::class.java)).thenReturn(response)

            presenter.getLeagueDetails(leagueId)
            Mockito.verify(view).showLoading()
            Mockito.verify(view).getLeagueDetails(leagues)
            Mockito.verify(view).hideLoading()
        }
    }

}