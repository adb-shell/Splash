package com.karthik.splash.homescreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.karthik.splash.R
import com.karthik.splash.homescreen.bottomhometab.BottomHomeTabFragment
import com.karthik.splash.homescreen.bottomliketab.BottomLikeTabFragment
import com.karthik.splash.homescreen.bottomsettingstab.BottomSettingsTabFragment
import com.karthik.splash.homescreen.di.HomeScreenComponent
import com.karthik.splash.homescreen.di.HomeScreenModule
import com.karthik.splash.root.SplashApp
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeScreen : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var homeScreenComponent: HomeScreenComponent? = null
    private val code = "code"

    @Inject
    lateinit var homescreenfactory: HomeScreenViewModelFactory
    private lateinit var homescreenviewmodel: HomeScreenViewModel


    companion object {
        const val IS_FROM_CACHE = "IS_FROM_CACHE"
        fun getIntent(context: Context, isFromCache: Boolean): Intent {
            val intent = Intent(context, HomeScreen::class.java)
            intent.putExtra(IS_FROM_CACHE, isFromCache)
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        homeScreenComponent = (application as SplashApp).getComponent()
                .plus(HomeScreenModule())
        homeScreenComponent?.inject(this)
        navigation.setOnNavigationItemSelectedListener(this)
        inflateHome()
        homescreenviewmodel = ViewModelProvider(this, homescreenfactory)
                .get(HomeScreenViewModel::class.java)
        homescreenviewmodel.userloginstate.observe(this, Observer { state ->
            if (state is HomeScreenLoginState.LoginFailed) {
                displayUnableToLogin()
                return@Observer
            }
            inflateLikes()
        })
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.data?.authority?.let {
            homescreenviewmodel.getUserInfo(intent.data?.getQueryParameter(code))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        homeScreenComponent = null
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.itemId == navigation.selectedItemId)
            return true

        when (menuItem.itemId) {
            R.id.navigation_home -> inflateHome()
            R.id.navigation_likes -> inflateLikes()
            R.id.navigation_settings -> inflateSettings()
        }
        return true
    }

    private fun inflateHome() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, BottomHomeTabFragment.getInstance(
                intent.getBooleanExtra(IS_FROM_CACHE, false)))
        transaction.commit()
    }

    private fun inflateLikes() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, BottomLikeTabFragment.getInstance())
        transaction.commit()
    }

    private fun inflateSettings() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, BottomSettingsTabFragment.getInstance())
        transaction.commit()
    }

    private fun displayUnableToLogin() =
            Toast.makeText(this, getString(R.string.unable_to_login), Toast.LENGTH_LONG).show()
}