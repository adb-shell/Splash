package com.karthik.splash.homescreen.bottomhometab

import com.karthik.splash.R
import java.util.ArrayList

class BottomHomeTabPresenter(private val view: BottomHomeTabContract.view):BottomHomeTabContract.presenter {

    override fun pageTitles(): ArrayList<String> {
        val context = view.getBottomHomeContext()
        if(context==null)
            return arrayListOf()
        return arrayListOf(context.getString(R.string.frag_title_1),
                context.getString(R.string.frag_title_2),context.getString(R.string.frag_title_3))
    }

}