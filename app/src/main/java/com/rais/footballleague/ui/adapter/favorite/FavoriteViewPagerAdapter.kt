package com.rais.footballleague.ui.adapter.favorite

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.rais.footballleague.ui.fragments.favorite.FavoriteMatchFragment
import com.rais.footballleague.ui.fragments.favorite.FavoriteTeamFragment

class FavoriteViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val listFragment : List<Fragment> = listOf(FavoriteMatchFragment(), FavoriteTeamFragment())

    override fun getItem(position: Int): Fragment {
        return listFragment[position]
    }

    override fun getCount(): Int = listFragment.size

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "Favorite Match"
            else -> "Favorite Team"
        }
    }
}