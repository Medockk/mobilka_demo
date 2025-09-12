package com.example.myapplication.presentation.SignUp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.AuthResponse
import com.example.myapplication.domain.model.SignUpRequest
import com.example.myapplication.domain.usecase.Auth.SignUpUseCase
import com.example.myapplication.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private val _state = mutableStateOf(SignUpState())
    val state: State<SignUpState> = _state

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.OnEmailChanged -> {
                _state.value = state.value.copy(
                    email = event.value,
                )
            }
            is SignUpEvent.OnNameChanged -> {
                _state.value = state.value.copy(
                    name = event.value,
                )
            }
            is SignUpEvent.OnPasswordChanged -> {
                _state.value = state.value.copy(
                    password = event.value,
                )
            }
            SignUpEvent.OnSignUpClick -> {
                _state.value = state.value.copy(
                    isLoading = true,
                )
                viewModelScope.launch(Dispatchers.IO) {
                    val request = SignUpRequest(
                        email = _state.value.email,
                        password = _state.value.password
                    )

                    try {
                        val result = signUpUseCase.invoke(request)
                        when (result) {
                            is Result.Error<String> -> {
                                _state.value = state.value.copy(
                                    exception = result.error,
                                    isLoading = false
                                )
                            }
                            Result.Loading -> Unit
                            is Result.Success<AuthResponse> -> {
                                _state.value = state.value.copy(
                                    isLoading = false,
                                    isRegistered = true,
                                    token = result.data.refreshToken
                                )
                            }
                        }
                    } catch (e: Exception) {
                        _state.value = state.value.copy(
                            exception = e.localizedMessage ?: "Unknown exception",
                        )
                    }
                }
            }
            SignUpEvent.ResetException -> {
                _state.value = state.value.copy(
                    exception = "",
                )
            }
        }
    }
}