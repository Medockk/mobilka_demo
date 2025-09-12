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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.myapplication.app.Route
import com.example.myapplication.domain.usecase.Auth.RefreshTokenUseCase
import com.example.myapplication.domain.utils.Result
import com.example.myapplication.presentation.SignIn.SignInScreen
import com.example.myapplication.presentation.SignUp.SignUpScreen
import com.example.myapplication.presentation.ui.theme.AppTheme
import com.example.myapplication.presentation.ui.theme.MyAppTheme
import com.example.myapplication.presentation.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
                                val viewModel = hiltViewModel<MainActivityViewModel>()
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Current token is: ${
                                            viewModel.state.value.ifBlank { token }
                                        }"
                                    )
                                    Spacer(Modifier.height(10.dp))
                                    Button(
                                        onClick = viewModel::refresh
                                    ) {
                                        Text(
                                            text = "Refresh"
                                        )
                                    }
                                    Text(
                                        text = viewModel.error.value
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val refreshTokenUseCase: RefreshTokenUseCase
) : ViewModel() {

    var state = mutableStateOf("")
        private set

    var error = mutableStateOf("")
        private set

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = refreshTokenUseCase.invoke()
            when (result) {
                is Result.Error<String> -> {
                    error.value = result.error
                }

                Result.Loading -> Unit
                is Result.Success<String> -> {
                    state.value = result.data
                }
            }
        }
    }
}