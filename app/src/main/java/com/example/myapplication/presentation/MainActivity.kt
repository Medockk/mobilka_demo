package com.example.myapplication.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.myapplication.app.Route
import com.example.myapplication.domain.model.RefreshResponseModel
import com.example.myapplication.domain.usecase.Auth.RefreshTokenUseCase
import com.example.myapplication.domain.utils.Result
import com.example.myapplication.presentation.SignIn.SignInScreen
import com.example.myapplication.presentation.SignUp.SignUpScreen
import com.example.myapplication.presentation.ui.theme.AppTheme
import com.example.myapplication.presentation.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            MyApplicationTheme {
                Scaffold(
                    modifier = Modifier.Companion.fillMaxSize(),
                    contentWindowInsets = WindowInsets.navigationBars
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Route.AuthGraph,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(AppTheme.colorScheme.background)
                            .padding(innerPadding)
                    ) {
                        navigation<Route.AuthGraph>(
                            startDestination = Route.AuthGraph.SignIn
                        ) {
                            composable<Route.AuthGraph.SignIn> {
                                SignInScreen(navController)
                            }
                            composable<Route.AuthGraph.SignUp> {
                                SignUpScreen(navController)
                            }
                        }
                        navigation<Route.MainGraph>(
                            startDestination = Route.MainGraph.MainScreen::class
                        ) {
                            composable<Route.MainGraph.MainScreen> {
                                val token = it.toRoute<Route.MainGraph.MainScreen>().token
                                MainScreen(token)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    token: String,
    viewModel: MainActivityViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Current token is: ${
                if (viewModel.state.value.isNullOrBlank()) {
                    token
                } else {
                    viewModel.state.value
                }
            }"
        )
        Spacer(Modifier.height(10.dp))
        Button(
            onClick = viewModel::refresh,
            enabled = !viewModel.isLoading.value
        ) {
            if (viewModel.isLoading.value) {
                CircularProgressIndicator()
            } else {
                Text(
                    text = "Refresh"
                )
            }
        }
        Text(
            text = viewModel.error.value
        )
    }
}

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val refreshTokenUseCase: RefreshTokenUseCase
) : ViewModel() {

    var state = mutableStateOf<String?>("")
        private set

    var error = mutableStateOf("")
        private set

    var isLoading = mutableStateOf(false)
        private set

    fun refresh() {
        isLoading.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val result = refreshTokenUseCase.invoke()
            when (result) {
                is Result.Error<*> -> {
                    error.value = result.error?.toString() ?: "unknown"
                    isLoading.value = false
                }

                Result.Loading -> Unit
                is Result.Success<RefreshResponseModel> -> {
                    withContext(Dispatchers.Main){
                        isLoading.value = false
                        state.value = result.data.idToken
                        error.value = ""
                    }
                }
            }
        }
    }
}