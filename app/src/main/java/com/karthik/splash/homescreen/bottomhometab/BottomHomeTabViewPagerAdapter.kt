package com.karthik.splash.homescreen.bottomhometab

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.karthik.splash.homescreen.bottomtab.BottomTabFragment
import com.karthik.splash.homescreen.bottomtab.BottomTabTypes
import java.util.ArrayList

internal class BottomHomeTabViewPagerAdapter(private val pagetitles: ArrayList<BottomTabTypes>,
                                    private val isfromcache: Boolean,
                                    fragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment =
            BottomTabFragment.getInstance(pagetitles[position], isfromcache)

    override fun getCount(): Int = pagetitles.size

    override fun getPageTitle(position: Int): CharSequence = pagetitles[position].title
}