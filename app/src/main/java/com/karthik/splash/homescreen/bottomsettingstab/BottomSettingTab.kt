package com.karthik.splash.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.karthik.splash.R
import com.karthik.splash.homescreen.bottomsettingstab.BottomSettingsViewModel
import com.karthik.splash.homescreen.bottomsettingstab.SettingsEvent
import com.karthik.splash.ui.Dimensions
import com.karthik.splash.ui.SplashTheme
import com.karthik.splash.ui.ToolBar
import com.karthik.splash.ui.getDividerColor

@Composable
@ExperimentalMaterialApi
fun BottomSettingTab(bottomsettingsviewmodel: BottomSettingsViewModel) {
    val state = bottomsettingsviewmodel.screenStatus.observeAsState()
    val settingsRowsData = bottomsettingsviewmodel.getSettingsRowData(state)
    SplashTheme {
        renderSettings(bottomsettingsviewmodel, settingsRowsData)
    }
}

@Composable
@ExperimentalMaterialApi
fun renderSettings(viewModel: BottomSettingsViewModel, list: List<SettingsEvent>) {
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
    settingsEvent: SettingsEvent,
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
private fun getRowText(viewModel: BottomSettingsViewModel, settingsEvent: SettingsEvent): String {
    return when (settingsEvent) {
        SettingsEvent.About -> {
            stringResource(id = R.string.about)
        }
        SettingsEvent.Logout -> {
            stringResource(id = R.string.logout)
        }
        SettingsEvent.LoggedIn -> {
            val username = viewModel.getUserName() ?: ""
            stringResource(id = R.string.logged_in_as, username)
        }
        SettingsEvent.NotLoggedIn -> {
            stringResource(id = R.string.login)
        }
        SettingsEvent.Downloads -> {
            stringResource(id = R.string.downloads)
        }
    }
}

@Composable
private fun getRowresourceId(settingsEvent: SettingsEvent): Painter {
    val resourceId = when (settingsEvent) {
        SettingsEvent.About -> {
            R.drawable.about
        }
        SettingsEvent.Downloads -> {
            R.drawable.downloads
        }
        SettingsEvent.LoggedIn,SettingsEvent.NotLoggedIn -> {
            R.drawable.heart
        }
        SettingsEvent.Logout -> {
            R.drawable.logout
        }
    }
    return painterResource(id = resourceId)
}

@ExperimentalMaterialApi
@Preview
@Composable
fun previewRow(){
   // renderRow(settingsEvent = SettingsEvent.About, {},)
}

@Preview
@Composable
fun previewRenderUI() {
    val dummyRowsData = mutableListOf(
        SettingsEvent.NotLoggedIn,
        SettingsEvent.About,
        SettingsEvent.Downloads
    )
    //renderSettings(list = dummyRowsData)
}
