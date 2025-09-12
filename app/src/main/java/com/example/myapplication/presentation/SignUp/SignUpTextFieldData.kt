package com.example.myapplication.presentation.SignUp

data class SignUpTextFieldData(
    val value: String,
    val onValueChange: (String) -> Unit,
    val placeholder: String,
)
