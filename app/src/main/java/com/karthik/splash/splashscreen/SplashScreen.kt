package com.karthik.splash.splashscreen

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.karthik.splash.R
import com.karthik.splash.Views.Feeds
import com.karthik.splash.root.SplashApp
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashScreen: AppCompatActivity(), SplashScreenContract.SplashView {

    private var splashScreenComponent: SplashScreenComponent?=null
    private val SPLASH_DELAY = 1000L

    @Inject
    lateinit var presenter: SplashScreenContract.SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        splashScreenComponent = (application as SplashApp).getComponent()
                .plus(SplashScreenModule(this))
        splashScreenComponent?.inject(this)
        Handler().postDelayed({ presenter.decideScreens() }, SPLASH_DELAY)
    }

    override fun getSplashScreenContext(): Context = this

    override fun showNoInternetScreen() {
        splashdisplaytext.text = getString(R.string.no_internet)
        splashimage.setImageResource(R.drawable.no_internet)
    }

    override fun showDashBoardScreen(shouldShowCache: Boolean) =
            startActivity(Feeds.getIntent(this, shouldShowCache))

    override fun closeScreen() = finish()

    override fun onDestroy() {
        super.onDestroy()
        splashScreenComponent = null
    }
}