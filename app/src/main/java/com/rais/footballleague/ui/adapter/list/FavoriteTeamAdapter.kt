package com.rais.footballleague.ui.adapter.list

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rais.footballleague.R
import com.rais.footballleague.model.FavoriteTeam
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find

class FavoriteTeamAdapter(private val context : Context, private val list : List<FavoriteTeam>, private val listener : (FavoriteTeam) -> Unit) : RecyclerView.Adapter<FavoriteTeamAdapter.FavoriteTeamViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteTeamViewHolder {
        return FavoriteTeamViewHolder(LayoutInflater.from(context).inflate(R.layout.list_team, parent, false))
    }

    override fun onBindViewHolder(holder: FavoriteTeamViewHolder, position: Int) {
        holder.bind(list[position],listener)
    }

    override fun getItemCount(): Int = list.size

    class FavoriteTeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val logo : ImageView = view.find(R.id.logo_team)
        private val teamName : TextView = view.find(R.id.team_name)

        @SuppressLint("SetTextI18n")
        fun bind(team : FavoriteTeam, listener: (FavoriteTeam) -> Unit) {
            Picasso.get().load(team.teamBadge).fit().into(logo)
            teamName.text = team.teamName
            itemView.setOnClickListener {
                listener(team)
            }
        }
    }

}