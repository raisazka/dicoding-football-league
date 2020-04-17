package com.rais.footballleague.match

import com.google.gson.Gson
import com.rais.footballleague.TestContextProvider
import com.rais.footballleague.model.Match
import com.rais.footballleague.model.Team
import com.rais.footballleague.model.responses.MatchResponse
import com.rais.footballleague.model.responses.TeamResponse
import com.rais.footballleague.network.ApiRepository
import com.rais.footballleague.presenter.match.MatchDetailPresenter
import com.rais.footballleague.presenter.match.MatchDetailView
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MatchDetailPresenterTest {

    @Mock
    private lateinit var view: MatchDetailView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse : Deferred<String>

    private lateinit var presenter : MatchDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchDetailPresenter(view, apiRepository, gson,
            TestContextProvider()
        )
    }

    @Test
    fun testMatchDetail() {
        val matches: MutableList<Match> = mutableListOf()
        val response = MatchResponse(matches)
        val matchId = 677703

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", MatchResponse::class.java)).thenReturn(response)

            presenter.getEventDetail(matchId)

            Mockito.verify(view).showLoading()
            Mockito.verify(view).getMatchDetail(matches)
            Mockito.verify(view).hideLoading()
        }
    }

    @Test
    fun testHomeLogo() {
        val matches: MutableList<Team> = mutableListOf()
        val response = TeamResponse(matches)
        val teamId = 133615

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", TeamResponse::class.java)).thenReturn(response)

            presenter.getHomeTeamData(teamId)
            view.getHomeTeam(matches)
        }
    }

    @Test
    fun testAwayLogo() {
        val matches: MutableList<Team> = mutableListOf()
        val response = TeamResponse(matches)
        val teamId = 133602

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", TeamResponse::class.java)).thenReturn(response)

            presenter.getAwayTeamData(teamId)
            view.getAwayTeam(matches)
        }
    }
}