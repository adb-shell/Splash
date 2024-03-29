package com.karthik.splash.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.coil.rememberCoilPainter
import com.karthik.network.home.bottomliketab.models.Photos
import com.karthik.network.home.bottomliketab.models.Urls
import com.karthik.splash.R
import com.karthik.splash.ui.Dimensions.Companion.sixteenDp

@Composable
fun SplashBrandLayout(
    imageResourceId: Int,
    content: String,
    modifier: Modifier,
    colorFilter: ColorFilter? = null
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = imageResourceId),
            contentDescription = content,
            colorFilter = colorFilter
        )
        Text(
            text = content,
            style = MaterialTheme.typography.h6.copy(
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@ExperimentalMaterialApi
@Composable
fun FeedRow(photo: Photos?, onClick: (Photos) -> Unit) {
    if (photo == null) return
    Surface(onClick = { onClick(photo) }) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimensions.twoHundredDp),
            painter = rememberCoilPainter(
                request = photo.urls?.regular,
                fadeIn = true
            ),
            contentDescription = photo.id,
            contentScale = ContentScale.FillWidth
        )
    }
}


@Composable
fun ToolBar(title: String) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(Dimensions.eightDp),
            style = MaterialTheme.typography.h6.copy(
                fontWeight = FontWeight.Normal
            )
        )
    }
}

@Composable
fun addVerticalGradient() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black.copy(
                            alpha = 0f
                        ),
                        Color.Black.copy(
                            alpha = 0.10f
                        ),
                        Color.Black.copy(
                            alpha = 0.33f
                        ),
                        Color.Black.copy(
                            alpha = 0.45f
                        ),
                        Color.Black.copy(
                            alpha = 0.65f
                        ),
                        Color.Black.copy(
                            alpha = 0.75f
                        ),
                        Color.Black
                    )
                )
            )
    )
}

@Composable
fun ProgressIndicator() {
    Box(contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
fun SplashBrandLayoutPreview() {
    SplashBrandLayout(
        imageResourceId = R.drawable.cold_image, "Splash", Modifier
            .padding(sixteenDp)
            .fillMaxSize()
    )
}


@Preview
@Composable
fun ToolBarPreview() {
    ToolBar(title = "Preview toolbar")
}

@Preview
@Composable
fun ProgressIndicatorPreview() {
    ProgressIndicator()
}

@ExperimentalMaterialApi
@Preview
@Composable
fun FeedsRowPreview() {
    FeedRow(
        photo = Photos(
            id = "1234",
            color = "#cccccc",
            numberLikes = "11",
            height = "123",
            createdTime = "122121212",
            urls = Urls(
                small = "https://developer.android.com/images/brand/Android_Robot.png",
                full = "https://developer.android.com/images/brand/Android_Robot.png",
                regular = "https://developer.android.com/images/brand/Android_Robot.png",
                thumb = "https://developer.android.com/images/brand/Android_Robot.png"
            ),
            user = null,
            width = "123"
        ),
        onClick = {photos ->  }
    )
}

@Preview
@Composable
fun GradientPreview() {
    addVerticalGradient()
}