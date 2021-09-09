package com.karthik.splash.homescreen.bottomsettingstab

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.karthik.network.home.models.HomeScreenLoginState
import com.karthik.splash.R
import com.karthik.splash.homescreen.HomeClickEvents
import com.karthik.splash.ui.Dimensions
import com.karthik.splash.ui.SplashTheme
import com.karthik.splash.ui.ToolBar
import com.karthik.splash.ui.getDividerColor

@Composable
@ExperimentalMaterialApi
fun BottomSettingTab(
    loginState: State<HomeScreenLoginState?>,
    bottomsettingsviewmodel: BottomSettingsViewModel
) {
    val settingsRowsData = bottomsettingsviewmodel.getSettingsRowData(loginState = loginState)
    SplashTheme {
        renderSettings(bottomsettingsviewmodel, settingsRowsData)
    }
}

@Composable
@ExperimentalMaterialApi
fun renderSettings(viewModel: BottomSettingsViewModel, list: List<HomeClickEvents>) {
    Surface {
        Column {
            ToolBar(title = stringResource(id = R.string.title_settings))
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Dimensions.fiveDp)
            )
            Card(
                elevation = Dimensions.fourDp,
                modifier = Modifier
                    .wrapContentWidth()
                    .wrapContentHeight()
                    .padding(Dimensions.sixteenDp)
            ) {
                Column{
                    list.forEach { settingsData ->
                        renderRow(viewModel = viewModel, settingsEvent = settingsData, onClick = {
                            viewModel.onClick(settingsEvent = settingsData)
                        })
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
private fun renderRow(
    viewModel: BottomSettingsViewModel,
    settingsEvent: HomeClickEvents,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        onClick = onClick
    ) {
        Column {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(Dimensions.fiveDp)
            ) {
                val rowText = getRowText(viewModel = viewModel,settingsEvent = settingsEvent)
                Image(
                    modifier = Modifier.padding(Dimensions.eightDp),
                    painter = getRowresourceId(settingsEvent = settingsEvent),
                    contentDescription = rowText,
                    colorFilter =
                    if (isSystemInDarkTheme()) ColorFilter.tint(color = Color.White) else null
                )
                Text(
                    text = rowText
                )
            }
            Divider(
                color = getDividerColor()
            )
        }
    }
}

@Composable
private fun getRowText(viewModel: BottomSettingsViewModel, settingsEvent: HomeClickEvents): String {
    return when (settingsEvent) {
        HomeClickEvents.AboutClick -> {
            stringResource(id = R.string.about)
        }
        HomeClickEvents.LogoutClick -> {
            stringResource(id = R.string.logout)
        }
        HomeClickEvents.LoginClick -> {
            val username = viewModel.getUserName() ?: ""
            stringResource(id = R.string.logged_in_as, username)
        }
        HomeClickEvents.NotLoggedIn -> {
            stringResource(id = R.string.login)
        }
        HomeClickEvents.DownloadsClick -> {
            stringResource(id = R.string.downloads)
        }
        else -> throw IllegalStateException("Unable to process the click event")
    }
}

@Composable
private fun getRowresourceId(settingsEvent: HomeClickEvents): Painter {
    val resourceId = when (settingsEvent) {
        HomeClickEvents.AboutClick -> {
            R.drawable.about
        }
        HomeClickEvents.DownloadsClick -> {
            R.drawable.downloads
        }
        HomeClickEvents.NotLoggedIn, HomeClickEvents.LoginClick -> {
            R.drawable.heart
        }
        HomeClickEvents.LogoutClick -> {
            R.drawable.logout
        }
        else -> throw IllegalStateException("Unable to process raw resource id")
    }
    return painterResource(id = resourceId)
}

/*@ExperimentalMaterialApi
@Preview
@Composable
fun previewRow(){
   // renderRow(settingsEvent = SettingsEvent.About, {},)
}*/

/*@Preview
@Composable
fun previewRenderUI() {
    val dummyRowsData = mutableListOf(
        HomeClickEvents.NotLoggedIn,
        HomeClickEvents.AboutClick,
        HomeClickEvents.DownloadsClick
    )
    renderSettings(list = dummyRowsData)
}*/
