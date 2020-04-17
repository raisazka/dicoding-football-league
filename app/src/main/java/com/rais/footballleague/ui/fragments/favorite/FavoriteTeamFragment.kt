package com.rais.footballleague.ui.fragments.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rais.footballleague.R
import com.rais.footballleague.helper.TeamDatabaseOpenHelper
import com.rais.footballleague.model.FavoriteTeam
import com.rais.footballleague.ui.activities.TeamDetailActivity
import com.rais.footballleague.ui.adapter.list.FavoriteTeamAdapter
import com.rais.footballleague.utils.invisible
import com.rais.footballleague.utils.visible
import kotlinx.android.synthetic.main.fragment_favorite_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.startActivity

class FavoriteTeamFragment : Fragment() {

    private var favoriteTeam : MutableList<FavoriteTeam> = mutableListOf()
    private lateinit var adapter : FavoriteTeamAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_team, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = FavoriteTeamAdapter(context!!, favoriteTeam) {
            startActivity<TeamDetailActivity>("teamId" to it.teamId)
        }
        rv_fav_team.adapter = adapter
        rv_fav_team.layoutManager = LinearLayoutManager(context!!)
    }

    override fun onResume() {
        super.onResume()
        getFavoriteTeam()
    }

    private fun getFavoriteTeam() {
        favoriteTeam.clear()
        val db = TeamDatabaseOpenHelper(context!!)
        db.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            favoriteTeam.addAll(favorite)
            if(favoriteTeam.isNotEmpty()) {
                no_team_found.invisible()
            } else {
                no_team_found.visible()
            }
            adapter.notifyDataSetChanged()
        }

    }

}
