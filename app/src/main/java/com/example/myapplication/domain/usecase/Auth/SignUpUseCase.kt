package com.example.myapplication.domain.usecase.Auth

import com.example.myapplication.domain.model.SignUpRequest
import com.example.myapplication.domain.repository.AuthRepository

class SignUpUseCase(
    private val repo: AuthRepository
) {

    suspend operator fun invoke(signUpRequest: SignUpRequest) =
        repo.signUp(signUpRequest)
}