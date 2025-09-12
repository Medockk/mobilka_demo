package com.example.myapplication.domain.usecase.Auth

import com.example.myapplication.domain.repository.AuthRepository

class RefreshTokenUseCase(
    private val repo: AuthRepository
) {

    suspend operator fun invoke() =
        repo.refreshToken()
}