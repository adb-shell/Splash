package com.karthik.splash.ui

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color


internal val blueGrey400 = Color(0xff455a64)
internal val blueGrey600 = Color(0xff1b3039)
internal val cyan200 = Color(0xffA2D4EB)
internal val cyan400 = Color(0xff7FA6B8)



val lightSplashColors = lightColors(
    primary = blueGrey400,
    primaryVariant = blueGrey600,
    secondary = cyan200,
    secondaryVariant = cyan400
)


val darkSplashColors = darkColors(
    primary = blueGrey600,
    secondary = cyan200,
)

