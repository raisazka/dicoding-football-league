package com.rais.footballleague.ui.activities

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.google.gson.Gson
import com.rais.footballleague.R
import com.rais.footballleague.model.League
import com.rais.footballleague.network.ApiRepository
import com.rais.footballleague.presenter.league.LeagueDetailPresenter
import com.rais.footballleague.presenter.league.LeagueDetailView
import com.rais.footballleague.ui.adapter.favorite.ViewPagerAdapter
import com.rais.footballleague.utils.invisible
import com.rais.footballleague.utils.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.cardview.v7.cardView
import org.jetbrains.anko.design.tabLayout
import org.jetbrains.anko.support.v4.viewPager

class LeagueActivity : AppCompatActivity(), LeagueDetailView {

    private lateinit var leagueProgressBar: ProgressBar

    private lateinit var leagueName : TextView

    private lateinit var leagueDesc : TextView

    private lateinit var leagueBase : TextView

    private lateinit var presenter : LeagueDetailPresenter

    private lateinit var btnStanding : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        val sharedPref: SharedPreferences = this.getSharedPreferences("prefs", 0)
        val leagueId = sharedPref.getInt("leagueIdPrefs", 0)
        val logo = intent.getIntExtra("leaguelogo", 0)
        linearLayout {
            backgroundResource = R.drawable.bg_purple
            orientation = LinearLayout.VERTICAL
            cardView {
                radius = 30f

                leagueProgressBar = progressBar {
                }.lparams {
                    gravity = Gravity.CENTER
                }

                verticalLayout {
                    padding = dip(20)
                    linearLayout {

                        imageView {
                            setImageResource(logo)
                        }.lparams(width = 250, height = 250)

                        linearLayout {
                            orientation = LinearLayout.VERTICAL
                            leagueName = textView {
                                textColor = Color.BLACK
                                textSize = 17f
                                apply {
                                    setTypeface(typeface, Typeface.BOLD)
                                }
                            }.lparams {
                                leftMargin = dip(30)
                            }
                            leagueDesc = textView {
                                textSize = 13f
                                textColor = Color.BLACK
                            }.lparams {
                                topMargin = dip(10)
                                leftMargin = dip(30)
                            }
                        }
                    }
                    leagueBase = textView {
                        textColor = Color.BLACK
                    }.lparams {
                        topMargin = dip(10)
                    }
                }

            }.lparams(width = 900, height = 500) {
                topMargin = dip(20)
                gravity = Gravity.CENTER
            }

            btnStanding = button {
                id = R.id.btnStanding
                backgroundColor = R.color.colorAccentRed
                textColor = Color.WHITE
                text = "Standing"
            }.lparams(width = 900, height = wrapContent) {
                topMargin = dip(10)
                gravity = Gravity.CENTER
            }

            tabLayout {
                id = R.id.tab_layout
                tabGravity = TabLayout.GRAVITY_FILL
                tabMode = TabLayout.MODE_FIXED
                setTabTextColors(Color.WHITE, Color.WHITE)
            }.lparams {
                width = matchParent
                topMargin = dip(20)
            }
           viewPager {
                    id = R.id.view_pager
            }.lparams(width = matchParent, height = matchParent)
        }
        val request = ApiRepository()
        val gson = Gson()

        val viewPager = find<ViewPager>(R.id.view_pager)
        val tabLayout = find<TabLayout>(R.id.tab_layout)

        viewPager.adapter =
            ViewPagerAdapter(
                supportFragmentManager
            )
        tabLayout.setupWithViewPager(viewPager)
        presenter =
            LeagueDetailPresenter(
                this,
                request,
                gson
            )
        presenter.getLeagueDetails(leagueId)
        btnStanding.setOnClickListener {
            startActivity<StandingActivity>("leagueId" to leagueId)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun getLeagueDetails(leagueList: List<League>) {
        leagueList.map {league ->
            leagueName.text = league.name
            leagueDesc.text = "${league.detail?.substring(0, 70)} ........"
            leagueBase.text = "Country Base: ${league.leagueBase}"
        }
    }

    override fun showLoading() {
        leagueProgressBar.visible()
    }

    override fun hideLoading() {
        leagueProgressBar.invisible()
    }
}