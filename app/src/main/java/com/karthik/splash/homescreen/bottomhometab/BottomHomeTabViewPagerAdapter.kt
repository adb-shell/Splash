package com.karthik.splash.homescreen.bottomhometab

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.karthik.splash.Views.HomeTabFeeds

class BottomHomeTabViewPagerAdapter(private val pagetitles:ArrayList<String>,
                                    private val isfromcache:Boolean,
                                    fragmentManager: FragmentManager): FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return when(position){
            1-> HomeTabFeeds.getInstance(1,isfromcache)
            2-> HomeTabFeeds.getInstance(2,isfromcache)
            else->HomeTabFeeds.getInstance(0,isfromcache)
        }
    }

    override fun getCount(): Int = pagetitles.size

    override fun getPageTitle(position: Int): CharSequence = pagetitles[position]
}