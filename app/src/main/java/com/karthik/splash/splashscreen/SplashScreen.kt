package com.karthik.splash.splashscreen



import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.karthik.splash.R
import com.karthik.splash.homescreen.HomeScreen
import com.karthik.splash.root.SplashApp
import com.karthik.splash.splashscreen.di.SplashScreenComponent
import com.karthik.splash.splashscreen.di.SplashScreenModule
import com.karthik.splash.ui.ActivityMargin
import com.karthik.splash.ui.SplashTheme
import com.karthik.splash.ui.splashBrandLayout
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
                splashScreen(
                    viewModel = splashscreenviewmodule
                )
            }
        }
    }

    @Composable
    fun splashScreen(viewModel: SplashScreenViewModel) {

        //initial splash screen state

        val state =
            viewModel.splashscreenstate.observeAsState(initial = SplashScreenState.SplashScreen)

        renderBasedOnState(state)
    }

    @Composable
    private fun renderBasedOnState(state: State<SplashScreenState>) {

        val modifier = Modifier
            .padding(ActivityMargin)
            .fillMaxSize()

        when (state.value) {
            SplashScreenState.NoInternetScreen -> {
                splashBrandLayout(
                    imageResourceId = R.drawable.no_internet,
                    content = getString(R.string.no_internet),
                    modifier = modifier
                )
            }
            SplashScreenState.SplashScreen -> {
                splashBrandLayout(
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

    private fun showDashBoardScreen(shouldShowCache: Boolean) =
            startActivity(HomeScreen.getIntent(this, shouldShowCache))
}
