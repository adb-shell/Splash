package com.karthik.splash.splashscreen


import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.karthik.splash.R
import com.karthik.splash.homescreen.HomeScreen
import com.karthik.splash.root.SplashApp
import com.karthik.splash.splashscreen.di.SplashScreenComponent
import com.karthik.splash.splashscreen.di.SplashScreenModule
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashScreen : AppCompatActivity(), SplashViewContract {
    private var splashScreenComponent: SplashScreenComponent? = null

    @Inject
    lateinit var splashviewmodulefactory: SplashScreenViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashScreenComponent = (application as SplashApp).getComponent()
                .plus(SplashScreenModule())

        splashScreenComponent?.inject(this)

        val splashscreenviewmodule = ViewModelProvider(this, splashviewmodulefactory)
                .get(SplashScreenViewModel::class.java)

        splashscreenviewmodule.splashscreenstate.observe(this, Observer<SplashScreenState> { state ->
            when (state) {
                SplashScreenState.FreshDashBoardScreen -> showDashBoardScreen(false)
                SplashScreenState.CachedDashBoardScreen -> showDashBoardScreen(true)
                SplashScreenState.NoInternetScreen -> showNoInternetScreen()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        splashScreenComponent = null
    }

    override fun getSplashScreenContext(): Context = this

    private fun showNoInternetScreen() {
        splashdisplaytext.text = getString(R.string.no_internet)
        splashimage.setImageResource(R.drawable.no_internet)
    }

    private fun showDashBoardScreen(shouldShowCache: Boolean) =
            startActivity(HomeScreen.getIntent(this, shouldShowCache))
}