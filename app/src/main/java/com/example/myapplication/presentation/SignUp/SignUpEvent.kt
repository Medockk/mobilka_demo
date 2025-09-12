package com.example.myapplication.presentation.SignUp

sealed interface SignUpEvent {

    data class OnEmailChanged(val value: String): SignUpEvent
    data class OnPasswordChanged(val value: String): SignUpEvent
    data class OnNameChanged(val value: String): SignUpEvent

    data object ResetException: SignUpEvent
    data object OnSignUpClick: SignUpEvent
}