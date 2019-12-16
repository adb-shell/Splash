package com.karthik.splash.homescreen.bottomhometab

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.karthik.splash.homescreen.bottomtab.BottomTabFragment
import com.karthik.splash.homescreen.bottomtab.BottomTabTypes
import java.util.ArrayList

class BottomHomeTabViewPagerAdapter(private val pagetitles: ArrayList<BottomTabTypes>,
                                    private val isfromcache:Boolean,
                                    fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment = BottomTabFragment.getInstance(pagetitles[position],isfromcache)

    override fun getCount(): Int = pagetitles.size

    override fun getPageTitle(position: Int): CharSequence{
        return pagetitles[position].title
    }
}