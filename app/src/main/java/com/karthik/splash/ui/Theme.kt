package com.karthik.splash.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.Composable

@Composable
fun SplashTheme(
    useDarkColors: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit) {
    MaterialTheme(
        colors = if (useDarkColors) darkSplashColors else lightSplashColors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}

interface ShapeConstants{
    companion object{
        internal const val ButtonRoundPercentage = 100
    }
}