package com.karthik.splash.splashscreen


import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.karthik.splash.R
import com.karthik.splash.homescreen.HomeScreen
import com.karthik.splash.root.SplashApp
import com.karthik.splash.splashscreen.di.SplashScreenComponent
import com.karthik.splash.splashscreen.di.SplashScreenModule
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashScreen: AppCompatActivity(),SplashViewContract{
    private val SPLASH_DELAY = 1000L
    private var splashScreenComponent: SplashScreenComponent?=null

    @Inject
    lateinit var splashScreenModule:SplashScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashScreenComponent = (application as SplashApp).getComponent()
                .plus(SplashScreenModule(this))
        splashScreenComponent?.inject(this)
        Handler().postDelayed({
            splashScreenModule.splashscreenstate.observe(this,Observer<SplashScreenState> { state->
                when(state){
                    SplashScreenState.FreshDashBoardScreen->showDashBoardScreen(false)
                    SplashScreenState.CachedDashBoardScreen->showDashBoardScreen(true)
                    SplashScreenState.NoInternetScreen->showNoInternetScreen()
                }
            })
        }, SPLASH_DELAY)
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