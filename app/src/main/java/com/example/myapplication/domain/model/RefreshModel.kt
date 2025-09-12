package com.example.myapplication.domain.model

data class RefreshResponseModel(
    val idToken: String,
    val refreshToken: String,
    val expiresIn: Long,
    val userId: String,
    val projectId: String,
    val tokenType: String,
    val accessToken: String
)