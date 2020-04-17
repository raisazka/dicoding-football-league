package com.rais.footballleague.ui.fragments.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.rais.footballleague.R
import com.rais.footballleague.helper.MatchDatabaseOpenHelper
import com.rais.footballleague.model.FavoriteMatch
import com.rais.footballleague.ui.activities.MatchDetailActivity
import com.rais.footballleague.ui.adapter.list.FavoriteMatchAdapter
import com.rais.footballleague.utils.invisible
import com.rais.footballleague.utils.visible
import kotlinx.android.synthetic.main.fragment_favorite_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity

class FavoriteMatchFragment : Fragment() {

    private var favoriteMatch : MutableList<FavoriteMatch> = mutableListOf()
    private lateinit var adapter : FavoriteMatchAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = FavoriteMatchAdapter(
            favoriteMatch
        ) {
            activity?.startActivity<MatchDetailActivity>("matchId" to it.matchId)
        }
        rv_favorite.layoutManager = LinearLayoutManager(activity)
        rv_favorite.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        getFavoriteMatch()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite_match, container, false)
    }

    private fun getFavoriteMatch() {
        favoriteMatch.clear()
        val db = MatchDatabaseOpenHelper.getInstance(context!!)
        db.use {
            val result = select(FavoriteMatch.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            favoriteMatch.addAll(favorite)
            if (favoriteMatch.isEmpty()) {
                no_favorite_text.visible()
            } else {
                no_favorite_text.invisible()
            }
            adapter.notifyDataSetChanged()
        }
    }

}
