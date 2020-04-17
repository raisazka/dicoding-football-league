package com.rais.footballleague.ui.adapter.list

import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rais.footballleague.R
import com.rais.footballleague.model.Match
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import java.util.*
import kotlin.collections.ArrayList

class MatchAdapter(private val matches : MutableList<Match>, private val listener: (Match) -> Unit)
    : RecyclerView.Adapter<MatchAdapter.MatchViewHolder>(), Filterable {

    private var matchListFull : ArrayList<Match>

    init {
        matchListFull = ArrayList(matches)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        return MatchViewHolder(
            MatchUI()
                .createView(AnkoContext.create(parent.context, parent))
        )
    }

    override fun getItemCount(): Int = matches.size

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bind(matches[position], listener)
    }

    class MatchUI : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>): View {
            return with(ui) {
                linearLayout {
                    padding = dip(20)
                    backgroundColor = R.color.colorPrimaryDark
                    cardView {
                        radius = 30f
                        elevation = 20f
                        linearLayout {
                            padding = dip(10)
                            orientation = LinearLayout.VERTICAL
                            textView {
                                id = R.id.match_name
                                textSize = 18f
                                this.setTypeface(null, Typeface.BOLD)
                                gravity = Gravity.CENTER_HORIZONTAL
                            }

                            textView {
                                id = R.id.match_time
                                textSize = 15f
                                gravity = Gravity.CENTER_HORIZONTAL
                            }

                            relativeLayout {

                                lparams {
                                    centerHorizontally()
                                }
                                textView {
                                    id = R.id.name_home
                                    textSize = 13f
                                    width = dip(80)
                                    gravity = Gravity.START
                                    this.setTypeface(null, Typeface.BOLD)
                                }.lparams {
                                    leftMargin = dip(30)
                                }

                                textView {
                                    id = R.id.score_home
                                    textSize = 17f
                                }.lparams {
                                    endOf(R.id.name_home)
                                    marginStart = dip(30)
                                }

                                textView {
                                    id = R.id.dot_score
                                    text = ":"
                                    textSize = 17f
                                }.lparams {
                                    endOf(R.id.score_home)
                                    marginStart = dip(15)
                                }

                                textView {
                                    id = R.id.score_away
                                    textSize = 17f
                                }.lparams {
                                    endOf(R.id.dot_score)
                                    marginStart = dip(15)
                                    alignParentRight()
                                }

                                textView {
                                    id = R.id.name_away
                                    width = dip(90)
                                    gravity = Gravity.END
                                    this.setTypeface(null, Typeface.BOLD)
                                }.lparams {
                                    alignParentRight()
                                    rightMargin = dip(20)
                                }
                            }
                        }.lparams(width = matchParent, height = wrapContent) {
                            gravity = Gravity.CENTER_HORIZONTAL
                        }
                    }
                }
            }
        }

    }

    class MatchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val nameHome = view.find<TextView>(R.id.name_home)
        private val nameAway = view.find<TextView>(R.id.name_away)
        private val matchName = view.find<TextView>(R.id.match_name)
        private val matchTime = view.find<TextView>(R.id.match_time)
        private val scoreHome = view.find<TextView>(R.id.score_home)
        private val scoreAway = view.find<TextView>(R.id.score_away)
        fun bind(match: Match, listener: (Match) -> Unit) {
            nameHome.text = match.homeTeamName
            nameAway.text = match.awayTeamName
            matchName.text = match.name
            matchTime.text = match.time
            if (match.scoreHome == null) {
                scoreHome.text =  "-"
            } else {
                scoreHome.text = match.scoreHome.toString()
            }
            if (match.scoreAway == null) {
                scoreAway.text  =  "-"
            } else {
                scoreAway.text = match.scoreAway.toString()
            }

            itemView.setOnClickListener {
                listener(match)
            }
        }
    }

    override fun getFilter(): Filter {
        return filter
    }

    private val filter : Filter = object : Filter(){
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = ArrayList<Match>()

            if(constraint == null || constraint.isEmpty()) {
                filteredList.addAll(matchListFull)
            } else {
                val pattern = constraint.toString().toLowerCase(Locale.ROOT).trim()
                matchListFull.forEach {
                    if(it.name!!.toLowerCase(Locale.ROOT).contains(pattern)) {
                        filteredList.add(it)
                    }
                }
            }
            val filteredResult = FilterResults()
            filteredResult.values = filteredList
            return filteredResult
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            matches.clear()
            matches.addAll(results!!.values as MutableList<Match>)
            notifyDataSetChanged()
        }

    }

}