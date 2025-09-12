package com.example.myapplication.data.data_source

import com.example.myapplication.data.model.AuthResponseDto
import com.example.myapplication.data.model.RefreshRequestDto
import com.example.myapplication.data.model.RefreshResponseDto
import com.example.myapplication.data.model.SignInRequestDto
import com.example.myapplication.data.model.SignUpRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {

    @POST("auth/signIn")
    suspend fun signIn(
        @Body signInRequestDto: SignInRequestDto
    ): Response<AuthResponseDto>

    @POST("auth/signUp")
    suspend fun signUp(
        @Body signUpRequestDto: SignUpRequestDto
    ): Response<AuthResponseDto>

    @POST("auth/refresh")
    suspend fun refreshToken(
        @Body refreshRequestModel: RefreshRequestDto
    ): Response<RefreshResponseDto>
}