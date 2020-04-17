package com.rais.footballleague.ui.adapter.favorite

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.rais.footballleague.ui.fragments.NextMatchFragment
import com.rais.footballleague.ui.fragments.PreviousMatchFragment
import com.rais.footballleague.ui.fragments.TeamFragment

class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val pages = listOf<Fragment>(PreviousMatchFragment(), NextMatchFragment(), TeamFragment())

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int =  pages.size

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "Previous Match"
            1 -> "Next Match"
            else -> "Teams"
        }
    }
}