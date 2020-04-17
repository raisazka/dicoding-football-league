package com.rais.footballleague.ui.adapter.list

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rais.footballleague.R
import com.rais.footballleague.model.TeamStanding
import org.jetbrains.anko.find
import org.jetbrains.anko.textColor

class StandingAdapter(private val context: Context, private val list: List<TeamStanding>) : RecyclerView.Adapter<StandingAdapter.StandingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StandingViewHolder {
        return StandingViewHolder(LayoutInflater.from(context).inflate(R.layout.list_standing, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: StandingViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class StandingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val teamName : TextView = view.find(R.id.stand_team_name)
        val played : TextView = view.find(R.id.stand_play)
        val gd : TextView = view.find(R.id.stand_gd)
        val win : TextView = view.find(R.id.stand_win)
        val draw : TextView = view.find(R.id.stand_draw)
        val loss : TextView = view.find(R.id.stand_loss)
        val pts : TextView = view.find(R.id.stand_pts)

        fun bind(standing: TeamStanding) {
            teamName.text = standing.teamName
            played.text = standing.played.toString()
            gd.text = standing.goalsdifference.toString()
            win.text = standing.win.toString()
            draw.text = standing.draw.toString()
            loss.text = standing.loss.toString()
            pts.text = standing.total.toString()
            win.textColor = Color.GREEN
            loss.textColor = Color.RED
            pts.textColor = Color.DKGRAY
            played.textColor = Color.BLUE
        }
    }

}