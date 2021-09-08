package com.karthik.splash.homescreen.bottomliketab

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.karthik.network.home.bottomliketab.models.Photos
import com.karthik.network.home.models.HomeScreenLoginState
import com.karthik.splash.R
import com.karthik.splash.ui.*

@ExperimentalMaterialApi
@Composable
fun BottomLikeTab(
    loginState: State<HomeScreenLoginState?>,
    bottomlikeviewmodel: BottomLikeViewModel
) {
    SplashTheme {

        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            when (loginState.value) {
                is HomeScreenLoginState.LoginSuccess -> {
                    renderLikePhotos(viewModel = bottomlikeviewmodel)
                }
                else ->{
                    renderLoginScreen {
                        bottomlikeviewmodel.loginClicked()
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun renderLikePhotos(viewModel: BottomLikeViewModel) {

    viewModel.getLikedPhotos()
    val screenState = viewModel.networkStatus.observeAsState()

    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        when (screenState.value) {
            is ScreenStatus.ShowProgress -> {
                ProgressIndicator()
            }
            is ScreenStatus.ErrorFetchingPhotos -> {
                renderErrorState()
            }
            is ScreenStatus.UserLikedPhotos -> {
                val photos = (screenState.value as ScreenStatus.UserLikedPhotos).likedPhotos
                if (!photos.isNullOrEmpty()) {
                    renderListOfLikedPhotos(viewModel = viewModel, likedPhotos = photos)
                } else renderErrorState()
            }
        }
    }
}

@Composable
private fun renderLoginScreen(onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(Dimensions.sixteenDp)
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
        Spacer(modifier = Modifier.height(Dimensions.twentyFourDp))
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimensions.twentyFourDp)
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

@Composable
private fun renderErrorState() {
    SplashBrandLayout(
        imageResourceId = R.drawable.broken_heart,
        content = stringResource(id = R.string.zero_likes),
        modifier = Modifier
            .padding(Dimensions.sixteenDp)
            .wrapContentHeight(align = Alignment.CenterVertically),
        colorFilter = if (isSystemInDarkTheme()) ColorFilter.tint(color = Color.White) else null
    )
}

@ExperimentalMaterialApi
@Composable
private fun renderListOfLikedPhotos(viewModel: BottomLikeViewModel, likedPhotos: List<Photos>) {
    Column {
        ToolBar(title = stringResource(id = R.string.title_likes))
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(likedPhotos) { likedPhoto ->
                FeedRow(likedPhoto) { photoClicked ->
                    viewModel.onPhotoItemClicked(photoClicked)
                }
            }
        }
    }
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
fun previewLikedPhotoState() {
    SplashTheme {

    }
}
