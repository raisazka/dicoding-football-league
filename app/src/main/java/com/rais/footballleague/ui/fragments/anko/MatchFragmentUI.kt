package com.rais.footballleague.ui.fragments.anko

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.rais.footballleague.R
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class MatchFragmentUI<T>(private val context: Context?) : AnkoComponent<T> {

    override fun createView(ui: AnkoContext<T>): View {
        return with(ui) {
            verticalLayout {
                layoutParams = ViewGroup.LayoutParams(matchParent, matchParent)
                backgroundColor = Color.WHITE
                recyclerView {
                    id = R.id.next_match
                    lparams(width = matchParent, height = wrapContent)
                    layoutManager = LinearLayoutManager(context)
                }
                progressBar {
                    id = R.id.prg_match
                }.lparams {
                    gravity = Gravity.CENTER
                }
            }
        }
    }
}