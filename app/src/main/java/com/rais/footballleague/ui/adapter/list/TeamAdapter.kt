package com.rais.footballleague.ui.adapter.list

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rais.footballleague.R
import com.rais.footballleague.model.Team
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find
import java.util.*
import kotlin.collections.ArrayList

class TeamAdapter(private val context : Context,private val list : MutableList<Team>,
                  private val listener : (Team) -> Unit) : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>(), Filterable {

    private var teamListFull : ArrayList<Team>

    init {
        teamListFull = ArrayList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        return TeamViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list_team, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bind(list[position], listener)
    }

    class TeamViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val logo : ImageView = view.find(R.id.logo_team)
        private val teamName : TextView = view.find(R.id.team_name)
        private val teamDesc : TextView = view.find(R.id.team_desc)

        @SuppressLint("SetTextI18n")
        fun bind(team : Team, listener: (Team) -> Unit) {
            Picasso.get().load(team.teamBadge).fit().into(logo)
            teamName.text = team.teamName
            teamDesc.text = "${team.teamDescription?.substring(0, 30)} ...."
            itemView.setOnClickListener {
                listener(team)
            }
        }
    }

    override fun getFilter(): Filter {
        return filter
    }

    private val filter : Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = ArrayList<Team>()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(teamListFull)
            } else {
                val pattern = constraint.toString().toLowerCase(Locale.ROOT).trim()
                teamListFull.forEach {
                    if(it.teamName.toLowerCase(Locale.ROOT).contains(pattern)) {
                        filteredList.add(it)
                    }
                }
            }
            val filteredResult = FilterResults()
            filteredResult.values = filteredList
            return filteredResult
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            list.clear()
            list.addAll(results!!.values as Collection<Team>)
            notifyDataSetChanged()
        }

    }
}