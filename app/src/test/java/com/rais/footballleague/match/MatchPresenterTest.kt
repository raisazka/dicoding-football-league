package com.rais.footballleague.match

import com.google.gson.Gson
import com.rais.footballleague.TestContextProvider
import com.rais.footballleague.model.Match
import com.rais.footballleague.model.responses.MatchResponse
import com.rais.footballleague.network.ApiRepository
import com.rais.footballleague.presenter.match.MatchPresenter
import com.rais.footballleague.presenter.match.MatchView
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MatchPresenterTest {

    @Mock
    private lateinit var view: MatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse : Deferred<String>

    private lateinit var presenter : MatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(view, apiRepository, gson,
            TestContextProvider()
        )
    }

    @Test
    fun testFutureMatch() {
        val listMatch : MutableList<Match> = mutableListOf()
        val response = MatchResponse(listMatch)
        val leagueId = 4328

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", MatchResponse::class.java)).thenReturn(response)

            presenter.getFutureMatches(leagueId)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).getMatches(listMatch)
            Mockito.verify(view).hideLoading()
        }
    }

    @Test
    fun testPastMatch() {
        val listMatch : MutableList<Match> = mutableListOf()
        val response = MatchResponse(listMatch)
        val leagueId = 4328

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", MatchResponse::class.java)).thenReturn(response)

            presenter.getPastMatches(leagueId)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).getMatches(listMatch)
            Mockito.verify(view).hideLoading()
        }
    }
}