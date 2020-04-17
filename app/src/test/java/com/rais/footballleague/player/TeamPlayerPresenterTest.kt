package com.rais.footballleague.player

import com.google.gson.Gson
import com.rais.footballleague.TestContextProvider
import com.rais.footballleague.model.Player
import com.rais.footballleague.model.responses.PlayerResponse
import com.rais.footballleague.network.ApiRepository
import com.rais.footballleague.presenter.team.TeamPlayerPresenter
import com.rais.footballleague.presenter.team.TeamPlayerView
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TeamPlayerPresenterTest {

    @Mock
    private lateinit var view: TeamPlayerView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse : Deferred<String>

    private lateinit var presenter : TeamPlayerPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = TeamPlayerPresenter(view, apiRepository, gson, TestContextProvider())

    }

    @Test
    fun getTeamPlayerTest() {
        val listPlayer : MutableList<Player> = mutableListOf()
        val response = PlayerResponse(listPlayer, listPlayer)
        val teamId = 133604

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString()))
                .thenReturn(apiResponse)

            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", PlayerResponse::class.java)).thenReturn(response)
            presenter.getTeamPlayers(teamId)
            Mockito.verify(view).showLoading()
            Mockito.verify(view).getTeamPlayers(listPlayer)
            Mockito.verify(view).hideLoading()
        }
    }

}