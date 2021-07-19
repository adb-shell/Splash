package com.karthik.splash.homescreen.bottomliketab

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.karthik.network.home.bottomliketab.models.Photos
import com.karthik.splash.R
import com.karthik.splash.homescreen.bottomliketab.di.BottomLikeTabComponent
import com.karthik.splash.homescreen.bottomliketab.di.BottomLikeTabModule
import com.karthik.splash.root.SplashApp
import com.karthik.splash.ui.*
import com.karthik.splash.ui.Dimensions.Companion.sixteenDp
import com.karthik.splash.ui.Dimensions.Companion.twentyFourDp
import javax.inject.Inject

class BottomLikeTabFragment : Fragment() {

    private var bottomLikeTabComponent: BottomLikeTabComponent? = null

    @Inject
    lateinit var viewModelFactory: BottomLikeViewModelFactory

    private lateinit var viewModel: BottomLikeViewModel

    companion object {
        fun getInstance(): BottomLikeTabFragment =
            BottomLikeTabFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomLikeTabComponent = (activity?.application as SplashApp).getComponent()
            .plus(BottomLikeTabModule())
        bottomLikeTabComponent?.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)
            .get(BottomLikeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                SplashTheme {
                    val screenState = viewModel.screenStatus.observeAsState()
                    renderUI(screenState = screenState)
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.clickEvent.observe(viewLifecycleOwner, { clickEvent ->
            if (clickEvent == ClickEvent.LOGIN_EVENT) {
                openLoginOauthUrl(oauthurl = viewModel.loginurl)
            }
        })
    }

    @Composable
    private fun renderUI(screenState: State<ScreenStatus?>) {
        Surface {
            when (screenState.value) {
                is ScreenStatus.ScreenLoggedIn -> {
                    viewModel.getLikedPhotos()
                }
                is ScreenStatus.ScreenNotLoggedIn -> {
                    renderLoginScreen {
                        viewModel.loginClicked()
                    }
                }
                is ScreenStatus.ShowProgress -> {
                    ProgressIndicator()
                }
                is ScreenStatus.ErrorFetchingPhotos -> {
                    renderErrorState()
                }
                is ScreenStatus.UserLikedPhotos -> {
                    val photos = (screenState.value as ScreenStatus.UserLikedPhotos).likedPhotos
                    if (!photos.isNullOrEmpty()) {
                        renderListOfLikedPhotos(likedPhotos = photos)
                    } else renderErrorState()
                }
            }
        }
    }

    @Composable
    private fun renderErrorState() {
        SplashBrandLayout(
            imageResourceId = R.drawable.broken_heart,
            content = stringResource(id = R.string.zero_likes),
            modifier = Modifier
                .padding(sixteenDp)
                .wrapContentHeight(align = Alignment.CenterVertically),
            colorFilter = if (isSystemInDarkTheme()) ColorFilter.tint(color = Color.White) else null
        )
    }

    @Composable
    private fun renderListOfLikedPhotos(likedPhotos: List<Photos>) {
        Column{
            ToolBar(title = stringResource(id = R.string.title_likes))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ){
                items(likedPhotos) { likedPhoto ->
                    FeedRow(likedPhoto)
                }
            }
        }
    }

    @Composable
    private fun renderLoginScreen(onClick: () -> Unit) {
        Column(
            modifier = Modifier
                .padding(sixteenDp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SplashBrandLayout(
                imageResourceId = R.drawable.cold_image,
                content = stringResource(id = R.string.welcome_back),
                modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically),
                colorFilter = if (isSystemInDarkTheme())
                    ColorFilter.tint(color = Color.White) else null
            )
            Text(
                text = stringResource(id = R.string.welcome_back_subtitle),
                style = MaterialTheme.typography.caption
            )
            Spacer(modifier = Modifier.height(twentyFourDp))
            Button(
                onClick = onClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(twentyFourDp)
                ) {
                Text(
                    text = stringResource(id = R.string.login),
                    style = MaterialTheme.typography.h6.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }

    private fun openLoginOauthUrl(oauthurl: String) {
        if (!isAdded)
            return
        val uri = Uri.parse(oauthurl)
        val intentBuilder = CustomTabsIntent.Builder()
        intentBuilder.setToolbarColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.icons
            )
        )
        intentBuilder.setSecondaryToolbarColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.icons
            )
        )
        val customTabsIntent = intentBuilder.build()
        customTabsIntent.launchUrl(requireContext(), uri)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bottomLikeTabComponent = null
    }

    @Composable
    @Preview
    fun previewLoginState() {
        SplashTheme() {
            renderLoginScreen({})
        }
    }

    @Composable
    @Preview
    fun previewErrorState() {
        SplashTheme {
            renderErrorState()
        }
    }

    @Composable
    @Preview
    fun previewLikedPhotoState(){
        SplashTheme {

        }
    }
}
