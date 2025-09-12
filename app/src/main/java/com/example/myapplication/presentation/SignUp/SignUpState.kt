package com.example.myapplication.presentation.SignUp

data class SignUpState(
    val exception: String = "",
    val isLoading: Boolean = false,
    val isRegistered: Boolean = false,

    val email: String = "",
    val password: String = "",
    val name: String = "",

    val token: String = "",
)
