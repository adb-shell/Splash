package com.karthik.splash.splashscreen



import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.karthik.splash.R
import com.karthik.splash.homescreen.HomeScreen
import com.karthik.splash.root.SplashApp
import com.karthik.splash.splashscreen.di.SplashScreenComponent
import com.karthik.splash.splashscreen.di.SplashScreenModule
import com.karthik.splash.ui.Dimensions.Companion.sixteenDp
import com.karthik.splash.ui.SplashTheme
import com.karthik.splash.ui.SplashBrandLayout
import javax.inject.Inject

class SplashScreen : AppCompatActivity() {
    private var splashScreenComponent: SplashScreenComponent? = null

    @Inject
    lateinit var splashviewmodulefactory: SplashScreenViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splashScreenComponent = (application as SplashApp).getComponent()
                .plus(SplashScreenModule())

        splashScreenComponent?.inject(this)

        val splashscreenviewmodule = ViewModelProvider(this, splashviewmodulefactory)
                .get(SplashScreenViewModel::class.java)

        setContent {
            SplashTheme{
                val state =
                    splashscreenviewmodule.splashscreenstate
                        .observeAsState(initial = SplashScreenState.SplashScreen)
                renderBasedOnState(state)
            }
        }
    }

    @Composable
    private fun renderBasedOnState(state: State<SplashScreenState>) {

        val modifier = Modifier
            .padding(sixteenDp)
            .fillMaxSize()

        when (state.value) {
            SplashScreenState.NoInternetScreen -> {
                SplashBrandLayout(
                    imageResourceId = R.drawable.no_internet,
                    content = getString(R.string.no_internet),
                    modifier = modifier
                )
            }
            SplashScreenState.SplashScreen -> {
                SplashBrandLayout(
                    imageResourceId = R.drawable.cold_image,
                    content = getString(R.string.app_name),
                    modifier = modifier
                )
            }
            SplashScreenState.FreshDashBoardScreen -> {
                showDashBoardScreen(shouldShowCache = false)
            }

            SplashScreenState.CachedDashBoardScreen -> {
                showDashBoardScreen(shouldShowCache = true)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        splashScreenComponent = null
    }

    private fun showDashBoardScreen(shouldShowCache: Boolean){
        startActivity(HomeScreen.getIntent(this, shouldShowCache))
        finish()
    }
}
