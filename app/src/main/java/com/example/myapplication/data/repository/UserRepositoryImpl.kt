package com.example.myapplication.data.repository

import com.example.myapplication.domain.model.AuthResponse
import com.example.myapplication.domain.repository.UserRepository
import com.example.myapplication.domain.utils.Result

class UserRepositoryImpl: UserRepository {

    override suspend fun getUserData(): Result<AuthResponse, String> {
        TODO("Not yet implemented")
    }
}