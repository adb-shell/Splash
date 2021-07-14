package com.karthik.splash.homescreen.bottomsettingstab

import android.Manifest
import android.app.DownloadManager
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.karthik.splash.R
import com.karthik.splash.aboutscreen.AboutScreen
import com.karthik.splash.homescreen.bottomsettingstab.di.BottomSettingsTabComponent
import com.karthik.splash.homescreen.bottomsettingstab.di.BottomSettingsTabModule
import com.karthik.splash.root.SplashApp
import com.karthik.splash.ui.*
import com.karthik.splash.ui.Dimensions.Companion.eightDp
import com.karthik.splash.ui.Dimensions.Companion.fiveDp
import com.karthik.splash.ui.Dimensions.Companion.fourDp
import com.karthik.splash.ui.Dimensions.Companion.sixteenDp
import javax.inject.Inject

@ExperimentalMaterialApi
class BottomSettingsTabFragment : Fragment() {

    private var bottomSettingsTabComponent: BottomSettingsTabComponent? = null
    private val permission = Manifest.permission.READ_EXTERNAL_STORAGE

    @Inject
    lateinit var viewModelFactory: BottomSettingsViewModelFactory
    private lateinit var viewModel: BottomSettingsViewModel

    companion object {
        fun getInstance() =
            BottomSettingsTabFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomSettingsTabComponent = (activity?.application as SplashApp).getComponent()
            .plus(BottomSettingsTabModule())
        bottomSettingsTabComponent?.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(BottomSettingsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val state = viewModel.userStatus.observeAsState()
                val settingsRowsData = viewModel.getSettingsRowData(state)
                SplashTheme {
                    renderUI(settingsRowsData)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.settingTypeClicked.observe(viewLifecycleOwner, { settingType ->
            when (settingType) {
                SettingsType.About -> {
                    context?.startActivity(Intent(context, AboutScreen::class.java))
                }
                SettingsType.Downloads -> {
                    if (ContextCompat.checkSelfPermission(requireContext(), permission)
                        == PermissionChecker.PERMISSION_GRANTED
                    ) {
                        openFolder()
                    } else {
                        requestPermissions(arrayOf(permission), 2)
                    }
                }
            }
        })
    }

    @Composable
    private fun renderUI(list: List<SettingsType>) {
        Surface {
            Column {
                TopAppBar(modifier = Modifier
                    .fillMaxWidth()) {
                    Text(
                        text = stringResource(id = R.string.title_settings),
                        modifier = Modifier.padding(eightDp),
                        style = MaterialTheme.typography.h6.copy(
                            fontWeight = FontWeight.Normal
                        )
                    )
                }
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(fiveDp)
                )
                Card(
                    elevation = fourDp,
                    modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .padding(sixteenDp)
                ) {
                    Column{
                        list.forEach { settingsData ->
                            renderRow(settingsType = settingsData, onClick = {
                                viewModel.onClick(settingsType = settingsData)
                            })
                        }
                    }
                }
            }
        }
    }


    @Composable
    private fun renderRow(settingsType: SettingsType, onClick: () -> Unit){
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
                    modifier = Modifier.padding(fiveDp)
                ) {
                    val rowText = getRowText(settingsType = settingsType)
                    Image(
                        modifier = Modifier.padding(eightDp),
                        painter = getRowresourceId(settingsType = settingsType),
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
    private fun getRowText(settingsType: SettingsType): String {
        return when (settingsType) {
            SettingsType.About -> {
                stringResource(id = R.string.about)
            }
            SettingsType.Logout -> {
                stringResource(id = R.string.logout)
            }
            SettingsType.LoggedIn -> {
                val username = viewModel.getUserName() ?: ""
                stringResource(id = R.string.logged_in_as, username)
            }
            SettingsType.NotLoggedIn -> {
                stringResource(id = R.string.login)
            }
            SettingsType.Downloads -> {
                stringResource(id = R.string.downloads)
            }
        }
    }

    @Composable
    private fun getRowresourceId(settingsType: SettingsType): Painter {
        val resourceId = when (settingsType) {
            SettingsType.About -> {
                R.drawable.about
            }
            SettingsType.Downloads -> {
                R.drawable.downloads
            }
            SettingsType.LoggedIn,SettingsType.NotLoggedIn -> {
                R.drawable.heart
            }
            SettingsType.Logout -> {
                R.drawable.logout
            }
        }
        return painterResource(id = resourceId)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bottomSettingsTabComponent = null
    }

    private fun openFolder() = startActivity(Intent(DownloadManager.ACTION_VIEW_DOWNLOADS))



    @Preview
    @Composable
    fun previewRow(){
        renderRow(settingsType = SettingsType.About, {})
    }

    @Preview
    @Composable
    fun previewRenderUI() {
        val dummyRowsData = mutableListOf(
            SettingsType.NotLoggedIn,
            SettingsType.About,
            SettingsType.Downloads
        )
       renderUI(list = dummyRowsData)
    }
}