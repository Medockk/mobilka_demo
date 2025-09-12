package com.example.myapplication.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

internal val LocalColorScheme = staticCompositionLocalOf { customLightColorScheme() }
internal val LocalTypography = staticCompositionLocalOf { Typography() }

@Composable
fun MyAppTheme(
    colorScheme: ColorScheme = AppTheme.colorScheme,
    typography: Typography = AppTheme.typography,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalColorScheme provides colorScheme,
        LocalTypography provides typography
    ){
        content()
    }
}

object AppTheme {

    val colorScheme: ColorScheme
        @Composable @ReadOnlyComposable get() = LocalColorScheme.current

    val typography: Typography
        @Composable @ReadOnlyComposable
        get() = LocalTypography.current
}