package com.karthik.splash.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.karthik.splash.ui.Color.Companion.blueGrey64
import com.karthik.splash.ui.Color.Companion.blueGrey39
import com.karthik.splash.ui.Color.Companion.cyanEB
import com.karthik.splash.ui.Color.Companion.cyanB8


interface Color {
    companion object{
        internal val blueGrey64 = Color(0xff455a64)
        internal val blueGrey39 = Color(0xff1b3039)
        internal val cyanEB = Color(0xffA2D4EB)
        internal val cyanB8 = Color(0xff7FA6B8)
    }
}

@Composable
fun getDividerColor() = if (isSystemInDarkTheme()) {
    Color.White.copy(alpha = 0.08f)
} else MaterialTheme.colors.onSurface.copy(alpha = 0.08f)

val lightSplashColors = lightColors(
    primary = blueGrey64,
    primaryVariant = blueGrey39,
    secondary = cyanEB,
    secondaryVariant = cyanB8
)


val darkSplashColors = darkColors(
    primary = cyanEB,
    secondary = blueGrey64,
)

