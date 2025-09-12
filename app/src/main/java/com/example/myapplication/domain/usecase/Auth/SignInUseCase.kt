package com.example.myapplication.domain.usecase.Auth

import com.example.myapplication.domain.model.SignInRequest
import com.example.myapplication.domain.repository.AuthRepository

class SignInUseCase(
    private val repo: AuthRepository
) {

    suspend operator fun invoke(signInRequest: SignInRequest) =
        repo.signIn(signInRequest)
}