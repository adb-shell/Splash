package com.karthik.splash.homescreen.bottomhometab

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.karthik.splash.R
import com.karthik.splash.homescreen.HomeScreen
import com.karthik.splash.root.SplashApp
import kotlinx.android.synthetic.main.fragment_bottom_tab_home.*
import javax.inject.Inject

class BottomHomeTabFragment: Fragment(),BottomHomeTabContract.view {

    private var bottomHomeTabComponent:BottomHomeTabComponent?=null

    @Inject
    lateinit var presenter:BottomHomeTabContract.presenter


    companion object{
        fun getInstance(iscached:Boolean): BottomHomeTabFragment {
            val args = Bundle()
            args.putBoolean(HomeScreen.IS_FROM_CACHE,iscached)
            val fragment = BottomHomeTabFragment()
            fragment.arguments = args
            fragment.retainInstance = true
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootview = inflater.inflate(R.layout.fragment_bottom_tab_home,container,false)
        bottomHomeTabComponent = (activity?.application as SplashApp).getComponent()
                .plus(BottomHomeTabModule(this))
        bottomHomeTabComponent?.inject(this)
        return rootview
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        intialiseTab()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bottomHomeTabComponent = null
    }

    override fun getBottomHomeContext(): Context? {
        if(!isAdded)
            return null
        return context
    }

    private fun intialiseTab() {
        viewpager.adapter = BottomHomeTabViewPagerAdapter(fragmentManager = childFragmentManager,
                pagetitles = presenter.pageTitles(),isfromcache = arguments!!.getBoolean(HomeScreen.IS_FROM_CACHE))
        viewpager.offscreenPageLimit = 3
        tabs.setupWithViewPager(viewpager)
    }
}