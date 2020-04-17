package com.rais.footballleague.ui.activities

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.rais.footballleague.R
import com.rais.footballleague.model.Match
import com.rais.footballleague.network.ApiRepository
import com.rais.footballleague.presenter.match.SearchMatchPresenter
import com.rais.footballleague.presenter.match.SearchMatchView
import com.rais.footballleague.ui.adapter.list.MatchAdapter
import com.rais.footballleague.utils.EspressoIdlingResource
import com.rais.footballleague.utils.invisible
import com.rais.footballleague.utils.visible
import kotlinx.android.synthetic.main.activity_search_match.*
import org.jetbrains.anko.startActivity

class SearchMatchActivity : AppCompatActivity(), SearchMatchView {

    private val matchList = mutableListOf<Match>()

    private lateinit var adapter: MatchAdapter

    lateinit var presenter: SearchMatchPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_match)
        val gson = Gson()

        val apiRepository = ApiRepository()

        adapter =
            MatchAdapter(matchList) { match ->
                startActivity<MatchDetailActivity>("matchId" to match.matchId)
            }

        EspressoIdlingResource.increment()
        presenter = SearchMatchPresenter(this, apiRepository, gson)
        rv_search_match.layoutManager = LinearLayoutManager(this)
        rv_search_match.adapter = adapter
        presenter.getMatch("")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_bar, menu)
        val searchMenu = menu!!.findItem(R.id.search_action)
        val searchView  = searchMenu.actionView as SearchView
        searchView.queryHint = "Search Match"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                presenter.getMatch(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                EspressoIdlingResource.increment()
                presenter.getMatch(newText)
                return false
            }

        })
        return true
    }

    override fun searchTeam(matches: List<Match>) {
        if(!EspressoIdlingResource.idlingresource.isIdleNow) {
            EspressoIdlingResource.decrement()
        }
        matchList.clear()
        matchList.addAll(matches)
        adapter.notifyDataSetChanged()
    }

    override fun hideText() {
        no_data.invisible()
    }

    override fun showText() {
        no_data.visible()
        matchList.clear()
    }


    override fun showLoading() {
        prg_search_match.visible()
    }

    override fun hideLoading() {
        prg_search_match.invisible()
    }
}