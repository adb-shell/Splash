package com.karthik.splash.homescreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import com.karthik.splash.R
import com.karthik.splash.Views.FeedsHome
import com.karthik.splash.Views.FeedsLike
import com.karthik.splash.Views.FeedsSettings
import com.karthik.splash.root.SplashApp
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeScreen: AppCompatActivity(),BottomNavigationView.OnNavigationItemSelectedListener, HomeScreenContract.View {

    private var homeScreenComponent:HomeScreenComponent?=null
    private val code = "code"

    @Inject
    lateinit var homeScreenPresenter: HomeScreenContract.Presenter


    companion object{
        const val IS_FROM_CACHE = "IS_FROM_CACHE"
        fun getIntent(context: Context,isFromCache:Boolean):Intent{
            val intent = Intent(context, HomeScreen::class.java)
            intent.putExtra(IS_FROM_CACHE,isFromCache)
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        homeScreenComponent = (application as SplashApp).getComponent()
                .plus(HomeScreenModule(this))
        homeScreenComponent?.inject(this)
        navigation.setOnNavigationItemSelectedListener(this)
        inflateHome()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if(intent!=null && intent.data!=null && intent.data?.authority!=null
                && !intent.data?.authority.isNullOrEmpty())
            homeScreenPresenter.getUserDetail(intent.data?.getQueryParameter(code))
    }

    override fun onDestroy() {
        super.onDestroy()
        homeScreenComponent = null
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        homeScreenPresenter.onNavigationItemSelected(menuItem.itemId)
        return true
    }

    override fun inflateHome() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, FeedsHome.getInstance(intent.getBooleanExtra(IS_FROM_CACHE, false)))
        transaction.commit()
    }

    override fun inflateLikes() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, FeedsLike.getInstance())
        transaction.commit()
    }

    override fun inflateSettings() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, FeedsSettings.getInstance())
        transaction.commit()
    }

    override fun getSelectedItem(): Int = navigation.selectedItemId

    override fun displayUnableToLogin() =
            Toast.makeText(this, getString(R.string.unable_to_login), Toast.LENGTH_LONG).show()
}