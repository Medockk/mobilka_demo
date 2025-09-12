package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.AuthResponse
import com.example.myapplication.domain.model.SignInRequest
import com.example.myapplication.domain.model.SignUpRequest
import com.example.myapplication.domain.utils.Result

interface AuthRepository {

    suspend fun signIn(signInRequest: SignInRequest): Result<AuthResponse, String>
    suspend fun signUp(signUpRequest: SignUpRequest): Result<AuthResponse, String>

    suspend fun refreshToken(): Result<String, String>
}