package com.example.myapplication.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequestDto(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean = true
)
@Serializable
data class SignUpRequestDto(
    val email: String,
    val password: String,
    val returnSecureToken: Boolean = true
)
@Serializable
data class AuthResponseDto(
    val uid: String,
    val email: String,
    val role: String,
    val idToken: String,
    val refreshToken: String,
    val expiresIn: Long
)
