package com.karthik.splash.homescreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.karthik.network.home.models.HomeScreenLoginState
import com.karthik.splash.homescreen.bottomhometab.BottomHomeTab
import com.karthik.splash.homescreen.bottomhometab.HomeViewModel
import com.karthik.splash.homescreen.bottomhometab.handleHomeClicks
import com.karthik.splash.homescreen.bottomhometab.tab.TabViewModel
import com.karthik.splash.homescreen.bottomhometab.tab.TabViewModelFactory
import com.karthik.splash.homescreen.bottomliketab.BottomLikeTab
import com.karthik.splash.homescreen.bottomliketab.BottomLikeViewModel
import com.karthik.splash.homescreen.bottomliketab.BottomLikeViewModelFactory
import com.karthik.splash.homescreen.bottomsettingstab.BottomSettingTab
import com.karthik.splash.homescreen.bottomsettingstab.BottomSettingsViewModel
import com.karthik.splash.homescreen.bottomsettingstab.BottomSettingsViewModelFactory
import com.karthik.splash.homescreen.di.HomeScreenComponent
import com.karthik.splash.homescreen.di.HomeScreenModule
import com.karthik.splash.root.SplashApp
import com.karthik.splash.ui.SplashTheme
import javax.inject.Inject

class HomeScreen : AppCompatActivity() {

    private var homeScreenComponent: HomeScreenComponent? = null
    private val code = "code"

    @Inject
    lateinit var homescreenfactory: HomeScreenViewModelFactory
    @Inject
    lateinit var bottomtabviewmodelfactory: TabViewModelFactory
    @Inject
    lateinit var bottomlikeviewmodelfactory: BottomLikeViewModelFactory
    @Inject
    lateinit var bottomsettingsviewmodelfactory: BottomSettingsViewModelFactory


    private lateinit var homescreenviewmodel: HomeScreenViewModel
    private lateinit var tabviewmodel: TabViewModel
    private lateinit var homeviewmodel: HomeViewModel
    private lateinit var bottomlikeviewmodel: BottomLikeViewModel
    private lateinit var bottomsettingsviewmodel: BottomSettingsViewModel


    companion object {
        const val IS_FROM_CACHE = "IS_FROM_CACHE"
        fun getIntent(context: Context, isFromCache: Boolean): Intent {
            val intent = Intent(context, HomeScreen::class.java)
            intent.putExtra(IS_FROM_CACHE, isFromCache)
            return intent
        }
    }


    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeScreenComponent = (application as SplashApp).getComponent()
            .plus(HomeScreenModule())
        homeScreenComponent?.inject(this)

        intialiseViewModels()

        handleViewModelEvents()

        renderUI()
    }

    private fun handleViewModelEvents() {
        bottomlikeviewmodel.clickEvent.observe(this, { clickevent ->
            handleHomeClicks(
                homeScreenViewModel = homescreenviewmodel,
                context = this,
                clickEvent = clickevent
            )
        })

        bottomsettingsviewmodel.clickEvent.observe(this, { clickevent ->
            handleHomeClicks(
                homeScreenViewModel = homescreenviewmodel,
                context = this,
                clickEvent = clickevent
            )
        })

        tabviewmodel.clickEvent.observe(this, { clickevent ->
            handleHomeClicks(
                homeScreenViewModel = homescreenviewmodel,
                context = this,
                clickEvent = clickevent
            )
        })
    }

    /**
     * If possible merge to use one view model across?
     */
    private fun intialiseViewModels() {
        homescreenviewmodel = ViewModelProvider(this, homescreenfactory)
            .get(HomeScreenViewModel::class.java)
        tabviewmodel = ViewModelProvider(this, bottomtabviewmodelfactory)
            .get(TabViewModel::class.java)
        homeviewmodel = ViewModelProvider(this).get(HomeViewModel::class.java)
        bottomlikeviewmodel = ViewModelProvider(this, bottomlikeviewmodelfactory)
            .get(BottomLikeViewModel::class.java)
        bottomsettingsviewmodel = ViewModelProvider(this, bottomsettingsviewmodelfactory)
            .get(BottomSettingsViewModel::class.java)
    }

    @ExperimentalMaterialApi
    private fun renderUI() {
        setContent {
            SplashTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        renderBottomBar(navController)
                    }
                ) {
                    renderMainScreen(navController)
                }
            }
        }
    }

    @Composable
    private fun renderBottomBar(navController: NavHostController) {
        BottomNavigation {
            val currentRoute = currentRoute(navController)
            homescreenviewmodel.getBottomNavigationItems().forEach { navigationScreen ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = navigationScreen.icon),
                            contentDescription = navigationScreen.route
                        )
                    },
                    label = { Text(text = navigationScreen.route) },
                    selected = currentRoute == navigationScreen.route,
                    alwaysShowLabel = false,
                    onClick = {
                        if (currentRoute != navigationScreen.route) {
                            navController.navigate(navigationScreen.route)
                        }
                    }
                )
            }
        }
    }

    @Composable
    private fun currentRoute(navController: NavHostController): String? {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        return navBackStackEntry?.destination?.route
    }

    @ExperimentalMaterialApi
    @Composable
    private fun renderMainScreen(navController: NavHostController) {
        val loginState = homescreenviewmodel.userloginstate.observeAsState()
        NavHost(navController, startDestination = BottomNavigationScreens.Home.route) {
            composable(BottomNavigationScreens.Home.route) {
                renderHomeScreen()
            }
            composable(BottomNavigationScreens.Like.route) {
                renderLikeScreen(loginState)
            }
            composable(BottomNavigationScreens.Settings.route) {
                renderSettingsScreen(loginState)
            }
        }
    }

    @ExperimentalMaterialApi
    @Composable
    private fun renderHomeScreen() {
        BottomHomeTab(homeviewmodel = homeviewmodel, tabviewmodel = tabviewmodel)
    }

    @ExperimentalMaterialApi
    @Composable
    private fun renderLikeScreen(loginState: State<HomeScreenLoginState?>) {
        BottomLikeTab(loginState = loginState, bottomlikeviewmodel = bottomlikeviewmodel)
    }

    @ExperimentalMaterialApi
    @Composable
    private fun renderSettingsScreen(loginState: State<HomeScreenLoginState?>) {
        BottomSettingTab(loginState = loginState, bottomsettingsviewmodel = bottomsettingsviewmodel)
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
}