package com.karthik.splash.ui

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color


internal val slateColorBD = Color(0xffbdbdbd)
internal val slateColor75 = Color(0xff757575)
internal val brownColor52 = Color(0xff755252)
internal val brownColor2F = Color(0xff752F2F)



val lightSplashColors = lightColors(
    primary = slateColorBD,
    primaryVariant = slateColor75,
    secondary = brownColor52,
    secondaryVariant = brownColor2F,
    onPrimary = Color.White,
    onSecondary = Color.White
)

//TODO: choose colors
val darkSplashColors = darkColors(
    primary = slateColorBD,
    primaryVariant = slateColor75,
    secondary = brownColor52,
    secondaryVariant = brownColor2F,
    onPrimary = Color.Black,
    onSecondary = Color.Black
)

