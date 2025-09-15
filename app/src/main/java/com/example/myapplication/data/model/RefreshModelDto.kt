package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshRequestDto(
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("grant_type")
    val grantType: String = "refresh_token"
)

@Serializable
data class RefreshResponseDto(
    @SerializedName("id_token") val idToken: String,
    @SerializedName("refresh_token") val refreshToken: String,
    @SerializedName("expires_in") val expiresIn: Long,
    @SerializedName("user_id") val userId: String,
    @SerializedName("project_id") val projectId: String,
    @SerializedName("token_type") val tokenType: String,
    @SerializedName("access_token") val accessToken: String
)