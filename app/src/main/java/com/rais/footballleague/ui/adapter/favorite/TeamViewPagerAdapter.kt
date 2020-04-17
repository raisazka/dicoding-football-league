package com.rais.footballleague.ui.adapter.favorite

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.rais.footballleague.ui.fragments.TeamDescriptionFragment
import com.rais.footballleague.ui.fragments.TeamPlayerFragment

class TeamViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    val listFragment : List<Fragment> = listOf(TeamDescriptionFragment(), TeamPlayerFragment())

    override fun getItem(position: Int): Fragment {
        return listFragment[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "Description"
            else -> "Players"
        }
    }

    override fun getCount(): Int = listFragment.size
}