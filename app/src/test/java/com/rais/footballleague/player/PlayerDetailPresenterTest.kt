package com.rais.footballleague.player

import com.google.gson.Gson
import com.rais.footballleague.TestContextProvider
import com.rais.footballleague.model.Player
import com.rais.footballleague.model.responses.PlayerResponse
import com.rais.footballleague.network.ApiRepository
import com.rais.footballleague.presenter.team.PlayerDetailPresenter
import com.rais.footballleague.presenter.team.PlayerDetailView
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PlayerDetailPresenterTest {

    @Mock
    private lateinit var view: PlayerDetailView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse : Deferred<String>

    private lateinit var presenter : PlayerDetailPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = PlayerDetailPresenter(view, gson, apiRepository, TestContextProvider())
    }

    @Test
    fun getPlayerDetailTest() {
        val player : MutableList<Player> = mutableListOf()
        val response = PlayerResponse(player, player)
        val playerId = 34145937

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", PlayerResponse::class.java)).thenReturn(response)

            presenter.getPlayerDetails(playerId)
            Mockito.verify(view).showLoading()
            Mockito.verify(view).getPlayerDetail(player)
            Mockito.verify(view).hideLoading()
        }
    }

}