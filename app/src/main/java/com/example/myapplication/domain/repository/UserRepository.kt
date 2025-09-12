package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.AuthResponse
import com.example.myapplication.domain.utils.Result

interface UserRepository {

    suspend fun getUserData(): Result<AuthResponse, String>
}