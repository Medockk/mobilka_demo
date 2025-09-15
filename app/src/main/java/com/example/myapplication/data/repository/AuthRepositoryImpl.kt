package com.example.myapplication.data.repository

import android.content.Context
import android.util.Log
import androidx.core.content.edit
import com.example.myapplication.data.data_source.AuthApi
import com.example.myapplication.data.mappers.toDto
import com.example.myapplication.data.mappers.toModel
import com.example.myapplication.data.model.RefreshRequestDto
import com.example.myapplication.domain.error.GlobalErrorBody
import com.example.myapplication.domain.model.AuthResponse
import com.example.myapplication.domain.model.RefreshResponseModel
import com.example.myapplication.domain.model.SignInRequest
import com.example.myapplication.domain.model.SignUpRequest
import com.example.myapplication.domain.repository.AuthRepository
import com.example.myapplication.domain.utils.Result
import com.google.gson.Gson

class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val context: Context
) : AuthRepository {

    private val key = "TOKEN"
    private val sp = context.getSharedPreferences(key, Context.MODE_PRIVATE)

    override suspend fun signIn(
        signInRequest: SignInRequest
    ): Result<AuthResponse, String> {

        val request = signInRequest.toDto()
        return try {
            val data = authApi.signIn(request)

            if (data.isSuccessful && data.body() != null) {
                sp.edit { clear().putString(key, data.body()!!.refreshToken) }
                Result.Success(data.body()!!.toModel())
            } else {
                Result.Error(data.errorBody()?.string() ?: "Unknown exception")
            }
        } catch (e: Exception) {
            Result.Error(e.localizedMessage)
        }
    }

    override suspend fun signUp(signUpRequest: SignUpRequest): Result<AuthResponse, GlobalErrorBody> {

        val request = signUpRequest.toDto()
        return try {
            val data = authApi.signUp(request)

            if (data.isSuccessful && data.body() != null) {
                sp.edit { clear().putString(key, data.body()!!.refreshToken) }
                Result.Success(data.body()!!.toModel())
            } else {
                Result.Error(
                    Gson().fromJson(
                        data.errorBody()?.string() ?: "Unknown exception",
                        GlobalErrorBody::class.java
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(
                Gson().fromJson(
                    e.localizedMessage ?: "Unknown exception",
                    GlobalErrorBody::class.java
                )
            )
        }
    }


    override suspend fun refreshToken(): Result<RefreshResponseModel, GlobalErrorBody> {
        val request = RefreshRequestDto(
            refreshToken = sp.getString(key, null) ?: return Result.Error(
                GlobalErrorBody(
                    "",
                    "token is null or empty",
                    400
                )
            ),
            grantType = "refresh_token"
        )
        return try {
            val result = authApi.refreshToken(request)
            Log.e("refreshToken", "${result.body()}")

            if (result.isSuccessful && result.body() != null) {
                sp.edit { clear().putString(key, result.body()!!.refreshToken) }
                Result.Success(result.body()!!.toModel())
            } else {
                Result.Error(
                    Gson().fromJson(
                        (result.errorBody()?.string() ?: "Unknown exception"),
                        GlobalErrorBody::class.java
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Error(GlobalErrorBody("${e.cause}", "${e.message}", 400))
        }
    }
}