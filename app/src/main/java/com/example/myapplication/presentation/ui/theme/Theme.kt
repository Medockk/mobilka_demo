package com.example.myapplication.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = customDarkColorScheme()

private val LightColorScheme = customLightColorScheme()
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme
    else LightColorScheme

    MyAppTheme(
        colorScheme = colorScheme,
        content = content
    )
}