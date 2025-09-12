package com.example.myapplication.presentation.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class ColorScheme(
    val mainColor: Color,
    val activeColor: Color,
    val bodyText: Color,
    val facebookColor: Color,
    val googleColor: Color,
    val background: Color
)

fun customLightColorScheme(
    mainColor: Color = light_mainColor,
    activeColor: Color = light_activeColor,
    bodyTextColor: Color = light_bodyTextColor,
    facebookColor: Color = light_facebookColor,
    googleColor: Color = light_googleColor,
    backgroundColor: Color = light_backgroundColor,
): ColorScheme =
    ColorScheme(
        mainColor = mainColor,
        activeColor = activeColor,
        bodyText = bodyTextColor,
        facebookColor = facebookColor,
        googleColor = googleColor,
        background = backgroundColor
    )

fun customDarkColorScheme(
    mainColor: Color = dark_mainColor,
    activeColor: Color = dark_activeColor,
    bodyTextColor: Color = dark_bodyTextColor,
    facebookColor: Color = dark_facebookColor,
    googleColor: Color = dark_googleColor,
    backgroundColor: Color = dark_backgroundColor
): ColorScheme =
    ColorScheme(
        mainColor = mainColor,
        activeColor = activeColor,
        bodyText = bodyTextColor,
        facebookColor = facebookColor,
        googleColor = googleColor,
        background = backgroundColor
    )