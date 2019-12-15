package com.karthik.splash.aboutscreen

import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.karthik.splash.Models.Library.Library
import com.karthik.splash.R
import kotlinx.android.synthetic.main.activity_about.*
import java.util.ArrayList

class AboutScreen: AppCompatActivity(), AppBarLayout.OnOffsetChangedListener {

    private var scrollrange=-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        librarieslist.layoutManager = LinearLayoutManager(this)
        librarieslist.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        librarieslist.adapter = LibraryAdapter(getLibraries())
        appbarlayout.addOnOffsetChangedListener(this)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
        if (scrollrange != -1 && scrollrange + verticalOffset == 0) {
            collapsingtoolbar.title = "About"
            expandedtitle.visibility = View.INVISIBLE
        } else {
            collapsingtoolbar.title = " "
            expandedtitle.visibility = View.VISIBLE
        }
        scrollrange = appBarLayout.totalScrollRange
    }

    private fun getLibraries(): ArrayList<Library> {
        val libraries = ArrayList<Library>()
        libraries.add(Library("Android support appcompat v7", "26.1.0"))
        libraries.add(Library("Android support v4", "26.1.0"))
        libraries.add(Library("Android support design", "26.1.0"))
        libraries.add(Library("Android support custom tabs", "26.1.0"))
        libraries.add(Library("Butterknife", "8.8.0"))
        libraries.add(Library("Butterknife compiler", "8.8.0"))
        libraries.add(Library("Retrofit 2", "2.3.0"))
        libraries.add(Library("Retrofit 2 converter GSON", "2.2.0"))
        libraries.add(Library("Retrofit 2 adapter rxjava 2", "2.3.0"))
        libraries.add(Library("Okhttp logging interceptor", "2.3.0"))
        libraries.add(Library("Dagger", "2.1.0"))
        libraries.add(Library("Dagger compiler", "2.1.0"))
        libraries.add(Library("Rx java 2", "2.1.6"))
        libraries.add(Library("Picasso", "2.5.2"))
        libraries.add(Library("Material image loading", "1.0.2"))
        libraries.add(Library("RxDownloader", "1.0.1"))
        libraries.add(Library("Chuck interceptor", "1.1.0"))
        libraries.add(Library("JUnit", "4.1.2"))
        libraries.add(Library("Test runner", "1.0.1"))
        libraries.add(Library("Expresso core", "3.0.1"))
        return libraries
    }
}