package com.example.myapplication.domain.model

data class SignInRequest(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean = true,
)

data class SignUpRequest(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean = true,
)

data class AuthResponse(
    val uid: String,
    val email: String,
    val role: String,
    val idToken: String,
    val refreshToken: String,
    val expiresIn: Long
)
