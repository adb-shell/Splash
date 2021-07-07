package com.karthik.splash.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.karthik.splash.R


@Preview
@Composable
fun SplashScreenContentPreview() {
    splashBrandLayout(
        imageResourceId = R.drawable.cold_image, "Splash", Modifier
            .padding(ActivityMargin)
            .fillMaxSize()
    )
}


@Composable
fun splashBrandLayout(
    imageResourceId: Int,
    content: String,
    modifier: Modifier,
    colorFilter: ColorFilter? = null) {
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