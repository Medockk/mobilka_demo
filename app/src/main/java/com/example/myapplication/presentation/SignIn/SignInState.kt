package com.example.myapplication.presentation.SignIn

data class SignInState(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isEmailVerified: Boolean? = null,

    val isLoading: Boolean = false,
    val isSuccessAuthentication: Boolean = false,
    val exception: String = "",
)
