package com.karthik.splash.homescreen.bottomhometab


import com.karthik.splash.R
import com.karthik.splash.homescreen.bottomtab.BottomTabTypes
import java.util.ArrayList

class BottomHomeTabPresenter(private val view: BottomHomeTabContract.view):BottomHomeTabContract.presenter {

    override fun pageTitles(): ArrayList<BottomTabTypes> {
        val context = view.getBottomHomeContext()
        if(context==null)
            return arrayListOf()
        return arrayListOf(BottomTabTypes.New(context.getString(R.string.frag_title_1)),
                BottomTabTypes.Trending(context.getString(R.string.frag_title_2)),
                BottomTabTypes.Featured(context.getString(R.string.frag_title_3)))
    }

}