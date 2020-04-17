package com.rais.footballleague.ui.adapter.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rais.footballleague.R
import com.rais.footballleague.model.League
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_league.view.*

class LeagueAdapter(val context: Context,val leagueList: List<League>,private val listener: (League) -> Unit) : RecyclerView.Adapter<LeagueAdapter.LeagueViewHolder>() {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        return LeagueViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_league, parent, false)
        )
    }

    override fun getItemCount(): Int = leagueList.size

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        holder.bind(leagueList[position], listener)
    }

    class LeagueViewHolder(v: View): RecyclerView.ViewHolder(v) {
        val name = v.league_name
        val logo = v.leage_logo

        fun bind(league: League, listener: (League) -> Unit) {
            name.text = league.name
            league.logo.let { Picasso.get().load(it).fit().into(logo) }
            itemView.setOnClickListener {
                listener(league)
            }
        }
    }

}