package com.example.myapplication.domain.error

data class GlobalErrorBody(
    val error: String,
    val message: String,
    val code: Int,
    val timestamp: Long = System.currentTimeMillis(),
)
