package com.example.myapplication.presentation.SignIn

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.domain.model.AuthResponse
import com.example.myapplication.domain.model.SignInRequest
import com.example.myapplication.domain.usecase.Auth.SignInUseCase
import com.example.myapplication.domain.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private val _state = mutableStateOf(SignInState())
    val state: State<SignInState> = _state

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.OnEmailChange -> {
                _state.value = state.value.copy(
                    email = event.value,
                )
            }
            is SignInEvent.OnPasswordChange -> {
                _state.value = state.value.copy(
                    password = event.value,
                )
            }
            SignInEvent.OnPasswordVisibilityChange -> {
                _state.value = state.value.copy(
                    isPasswordVisible = !_state.value.isPasswordVisible
                )
            }
            SignInEvent.OnSignInClick -> {
                _state.value = state.value.copy(
                    isLoading = true,
                )
                viewModelScope.launch(Dispatchers.IO) {
                    val request = SignInRequest(
                        _state.value.email,
                        _state.value.password
                    )
                    val result = signInUseCase.invoke(request)

                    when (result) {
                        is Result.Error<String> -> {
                            _state.value = state.value.copy(
                                exception = result.error,
                                isLoading = false
                            )
                        }
                        Result.Loading -> Unit
                        is Result.Success<AuthResponse> -> {
                            Log.e("onEvent", "${result.data}")
                            withContext(Dispatchers.Main) {
                                _state.value = state.value.copy(
                                    isLoading = false,
                                    isSuccessAuthentication = true,
                                    token = result.data.idToken
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}