package com.example.myapplication.data.mappers

import com.example.myapplication.data.model.RefreshResponseDto
import com.example.myapplication.domain.model.RefreshResponseModel

fun RefreshResponseDto.toModel() =
    RefreshResponseModel(
        idToken = idToken,
        refreshToken = refreshToken,
        expiresIn = expiresIn,
        userId = userId,
        projectId = projectId,
        tokenType = tokenType,
        accessToken = accessToken
    )