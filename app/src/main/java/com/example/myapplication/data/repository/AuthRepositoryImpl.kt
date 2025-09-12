package com.example.myapplication.data.repository

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import com.example.myapplication.data.data_source.AuthApi
import com.example.myapplication.data.mappers.toDto
import com.example.myapplication.data.mappers.toModel
import com.example.myapplication.data.model.RefreshRequestDto
import com.example.myapplication.domain.model.AuthResponse
import com.example.myapplication.domain.model.SignInRequest
import com.example.myapplication.domain.model.SignUpRequest
import com.example.myapplication.domain.repository.AuthRepository
import com.example.myapplication.domain.utils.Result

class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val context: Context
): AuthRepository {

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
            }else {
                Result.Error(data.errorBody()?.string() ?: "Unknown exception")
            }
        } catch (e: Exception) {
            Result.Error(e.localizedMessage)
        }
    }

    override suspend fun signUp(signUpRequest: SignUpRequest): Result<AuthResponse, String> {

        val request = signUpRequest.toDto()
        return try {
            val data = authApi.signUp(request)

            if (data.isSuccessful && data.body() != null) {
                sp.edit { clear().putString(key, data.body()!!.refreshToken) }
                Result.Success(data.body()!!.toModel())
            } else {
                Result.Error(data.errorBody()?.string() ?: "Unknown exception")
            }
        }catch (e: Exception){
            Result.Error(e.localizedMessage)
        }
    }

    override suspend fun refreshToken(): Result<String, String> {
        val request = RefreshRequestDto(
            refreshToken = sp.getString(key, "")!!
        )
        return try {
            val result = authApi.refreshToken(request)

            if (result.isSuccessful && result.body() != null) {
                sp.edit { clear().putString(key, result.body()!!.refreshToken) }
                Result.Success(result.body()!!.refreshToken)
            } else {
                Log.e("refreshToken", "${result.raw()}")
                Result.Error(result.errorBody()?.string() ?: "Unknown")
            }
        } catch (e: Exception) {
            Result.Error(e.localizedMessage)
        }
    }
}