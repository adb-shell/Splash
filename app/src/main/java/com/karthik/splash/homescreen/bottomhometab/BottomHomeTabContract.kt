package com.karthik.splash.homescreen.bottomhometab

import android.content.Context

import java.util.ArrayList

/**
 * Created by karthikrk on 15/11/17.
 */

interface BottomHomeTabContract {
    interface view {
        fun getBottomHomeContext(): Context?
    }

    interface presenter {
        fun pageTitles(): ArrayList<String>
    }
}
