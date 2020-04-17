package com.rais.footballleague.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.rais.footballleague.R
import com.rais.footballleague.model.Match
import com.rais.footballleague.network.ApiRepository
import com.rais.footballleague.presenter.match.MatchPresenter
import com.rais.footballleague.presenter.match.MatchView
import com.rais.footballleague.ui.activities.MatchDetailActivity
import com.rais.footballleague.ui.adapter.list.MatchAdapter
import com.rais.footballleague.ui.fragments.anko.MatchFragmentUI
import com.rais.footballleague.utils.invisible
import com.rais.footballleague.utils.visible
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.startActivity

class PreviousMatchFragment : Fragment(),
    MatchView {

    private val matchList = mutableListOf<Match>()

    lateinit private var adapter : MatchAdapter

    lateinit var progressBar : ProgressBar

    lateinit var presenter : MatchPresenter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?  = MatchFragmentUI<Fragment>(activity).createView(AnkoContext.create(requireContext(), this))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar  = find(R.id.prg_match)
        val sharedPref: SharedPreferences = this.activity!!.getSharedPreferences("prefs", 0)

        val recyclerView = find<RecyclerView>(R.id.next_match)

        val apiRepository = ApiRepository()

        val gson = Gson()

        val leagueId = sharedPref.getInt("leagueIdPrefs", 0)

        presenter = MatchPresenter(
            this,
            apiRepository,
            gson
        )
        presenter.getPastMatches(leagueId)
        adapter =
            MatchAdapter(matchList) { match ->
                startActivity<MatchDetailActivity>("matchId" to match.matchId)
            }
        recyclerView.adapter = adapter
    }

    override fun getMatches(matches: List<Match>) {
        matchList.clear()
        matchList.addAll(matches)
        adapter.notifyDataSetChanged()
    }


    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

}


