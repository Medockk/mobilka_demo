package com.example.myapplication.presentation.SignIn

sealed interface SignInEvent {

    data class OnEmailChange(val value: String): SignInEvent
    data class OnPasswordChange(val value: String): SignInEvent
    data object OnPasswordVisibilityChange: SignInEvent

    data object OnSignInClick: SignInEvent
}