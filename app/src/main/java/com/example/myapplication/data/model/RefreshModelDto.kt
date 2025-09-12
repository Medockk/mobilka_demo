package com.example.myapplication.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshRequestDto(
    @SerialName("refresh_token")
    val refreshToken: String,
    @SerialName("grant_type")
    val grantType: String = "refresh_token"
)

@Serializable
data class RefreshResponseDto(
    @SerialName("id_token") val idToken: String,
    @SerialName("refresh_token") val refreshToken: String,
    @SerialName("expires_in") val expiresIn: Long,
    @SerialName("user_id") val userId: String,
    @SerialName("project_id") val projectId: String,
    @SerialName("token_type") val tokenType: String,
    @SerialName("access_token") val accessToken: String
)