package com.rais.footballleague.ui.adapter.list

import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rais.footballleague.R
import com.rais.footballleague.model.FavoriteMatch
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView

class FavoriteMatchAdapter(private val favorites : List<FavoriteMatch>, private val listener: (FavoriteMatch) -> Unit)
    :RecyclerView.Adapter<FavoriteMatchAdapter.FavoriteViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        return FavoriteViewHolder(
            MatchUI()
                .createView(AnkoContext.create(parent.context, parent))
        )
    }

    override fun getItemCount(): Int = favorites.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favorites[position], listener)
    }

    class MatchUI : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>): View {
            return with(ui) {
                linearLayout {
                    padding = dip(20)
                    cardView {
                        radius = 30f
                        elevation = 30f
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

    class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameHome = view.find<TextView>(R.id.name_home)
        val nameAway = view.find<TextView>(R.id.name_away)
        val matchName = view.find<TextView>(R.id.match_name)
        val matchTime = view.find<TextView>(R.id.match_time)
        val scoreHome = view.find<TextView>(R.id.score_home)
        val scoreAway = view.find<TextView>(R.id.score_away)
        fun bind(favoriteMatch: FavoriteMatch, listener: (FavoriteMatch) -> Unit) {
            nameHome.text = favoriteMatch.homeName
            nameAway.text = favoriteMatch.awayName
            matchName.text = favoriteMatch.matchName
            matchTime.text = favoriteMatch.matchDate
            if (favoriteMatch.homeScore == null) {
                scoreHome.text =  "-"
            } else {
                scoreHome.text = favoriteMatch.homeScore.toString()
            }
            if (favoriteMatch.awayScore == null) {
                scoreAway.text  =  "-"
            } else {
                scoreAway.text = favoriteMatch.awayScore.toString()
            }

            itemView.setOnClickListener {
                listener(favoriteMatch)
            }
        }
    }
}