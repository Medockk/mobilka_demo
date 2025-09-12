package com.example.myapplication.domain.utils

sealed interface Result<out DATA,out ERROR> {

    data class Success<DATA>(val data: DATA): Result<DATA, Nothing>
    data class Error<ERROR>(val error: ERROR): Result<Nothing, ERROR>

    data object Loading: Result<Nothing, Nothing>
}