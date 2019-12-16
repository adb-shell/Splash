package com.karthik.splash.homescreen.bottomtab

/**
 * Created by karthikrk on 23/11/17.
 */

interface PaginatedView {
    fun getMaxPageLimit(): Int
    fun getPage(pageNo: Int)
}
