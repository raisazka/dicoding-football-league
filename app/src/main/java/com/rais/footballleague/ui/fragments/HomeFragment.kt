package com.rais.footballleague.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.rais.footballleague.R
import com.rais.footballleague.model.League
import com.rais.footballleague.ui.activities.LeagueActivity
import com.rais.footballleague.ui.adapter.list.LeagueAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.startActivity

class HomeFragment : Fragment() {

    val leagueList : MutableList<League> = mutableListOf()

    lateinit var sharedPref: SharedPreferences

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        load()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun load() {
        val names = resources.getStringArray(R.array.leagueName)
        val desc = resources.getStringArray(R.array.leagueDetail)
        val logo = resources.obtainTypedArray(R.array.leageLogo)
        val ids = resources.getIntArray(R.array.leagueId)
        sharedPref = activity!!.getSharedPreferences("prefs", 0)

        for(i in names.indices) {
            leagueList.add(League(ids[i],names[i], desc[i], null, logo.getResourceId(i, 0)))
        }

        logo.recycle()
        val adapter = LeagueAdapter(
            context!!,
            leagueList
        ) { league ->
            val editor = sharedPref.edit()
            editor.putInt("leagueIdPrefs", league.leagueId)
            editor.apply()
            activity!!.startActivity<LeagueActivity>("leaguelogo" to league.logo)
        }
        rv_league.adapter = adapter
        rv_league.layoutManager = GridLayoutManager(context!!, 2)
    }
}
