package com.soyvictorherrera.nosedive.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Wild_Watermelon,
    primaryVariant = Shiraz,
    secondary = Dove_Gray,
    onPrimary = Dove_Gray
)

private val LightColorPalette = lightColors(
    primary = Wild_Watermelon,
    primaryVariant = Shiraz,
    secondary = Dove_Gray

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun NosediveTheme(
    darkTheme: Boolean = true,
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}