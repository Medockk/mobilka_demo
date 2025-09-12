package com.example.myapplication.data.mappers

import com.example.myapplication.data.model.AuthResponseDto
import com.example.myapplication.data.model.SignInRequestDto
import com.example.myapplication.data.model.SignUpRequestDto
import com.example.myapplication.domain.model.AuthResponse
import com.example.myapplication.domain.model.SignInRequest
import com.example.myapplication.domain.model.SignUpRequest

fun SignInRequest.toDto() =
    SignInRequestDto(
        email = email,
        password = password,
        returnSecureToken = returnSecureToken,
    )
fun SignUpRequest.toDto() =
    SignUpRequestDto(
        email = email,
        password = password,
        returnSecureToken = returnSecureToken,
    )

fun SignInRequestDto.toModel() =
    SignInRequest(
        email = email,
        password = password,
        returnSecureToken = returnSecureToken,
    )
fun SignUpRequestDto.toModel() =
    SignUpRequest(
        email = email,
        password = password,
        returnSecureToken = returnSecureToken,
    )
fun AuthResponseDto.toModel() =
    AuthResponse(
        uid = uid,
        email = email,
        role = role,
        idToken = idToken,
        refreshToken = refreshToken,
        expiresIn = expiresIn,
    )
fun AuthResponse.toDto() =
    AuthResponseDto(
        uid = uid,
        email = email,
        role = role,
        idToken = idToken,
        refreshToken = refreshToken,
        expiresIn = expiresIn,
    )
