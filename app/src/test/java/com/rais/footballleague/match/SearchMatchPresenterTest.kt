package com.rais.footballleague.match

import com.google.gson.Gson
import com.rais.footballleague.TestContextProvider
import com.rais.footballleague.model.Match
import com.rais.footballleague.model.responses.SearchMatchResponse
import com.rais.footballleague.network.ApiRepository
import com.rais.footballleague.presenter.match.SearchMatchPresenter
import com.rais.footballleague.presenter.match.SearchMatchView
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchMatchPresenterTest {

    @Mock
    private lateinit var view: SearchMatchView

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var apiRepository: ApiRepository

    @Mock
    private lateinit var apiResponse : Deferred<String>

    private lateinit var presenter : SearchMatchPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = SearchMatchPresenter(view, apiRepository, gson,
            TestContextProvider()
        )
    }

    @Test
    fun searchMatchTest() {
        val listMatch : MutableList<Match> =  mutableListOf()
        val response = SearchMatchResponse(listMatch)
        val matchName = "Barcelona"

        runBlocking {
            Mockito.`when`(apiRepository.doRequest(ArgumentMatchers.anyString())).thenReturn(apiResponse)
            Mockito.`when`(apiResponse.await()).thenReturn("")
            Mockito.`when`(gson.fromJson("", SearchMatchResponse::class.java)).thenReturn(response)

            presenter.getMatch(matchName)
            Mockito.verify(view).showLoading()
            Mockito.verify(view).searchTeam(listMatch)
            Mockito.verify(view).hideLoading()
        }
    }

}