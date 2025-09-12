package com.example.myapplication.presentation.SignUp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.app.Route
import com.example.myapplication.presentation.common.PrimaryButton
import com.example.myapplication.presentation.common.PrimaryTopAppBar

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
) {

    val state = viewModel.state.value
    val textFields = listOf(
        SignUpTextFieldData(
            value = state.name,
            onValueChange = {
                viewModel.onEvent(SignUpEvent.OnNameChanged(it))
            },
            placeholder = "User name"
        ),
        SignUpTextFieldData(
            value = state.email,
            onValueChange = {
                viewModel.onEvent(SignUpEvent.OnEmailChanged(it))
            },
            placeholder = "Email"
        ),
        SignUpTextFieldData(
            value = state.password,
            onValueChange = {
                viewModel.onEvent(SignUpEvent.OnPasswordChanged(it))
            },
            placeholder = "Password"
        ),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        PrimaryTopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.sign_up)
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.back_icon),
                        null
                    )
                }
            }
        )

        Spacer(Modifier.height(15.dp))

        Text(
            text = stringResource(R.string.welcome_to_tamang_food_services)
        )
        Spacer(Modifier.height(7.dp))
        Text(
            text = stringResource(R.string.enter_your_phone_number_or_email_address_for_sign_in_enjoy_your_food)
        )

        Spacer(Modifier.height(25.dp))

        textFields.forEach { each ->
            TextField(
                value = each.value,
                onValueChange = each.onValueChange,
                modifier = Modifier
                    .fillMaxWidth(),
                placeholder = {
                    Text(
                        text = each.placeholder
                    )
                }
            )
            Spacer(Modifier.height(20.dp))
        }

        Spacer(Modifier.height(20.dp))
        Text(
            text = "Already have an account? Sign In",
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate(Route.AuthGraph.SignIn)
                }.padding(10.dp)
        )
        Spacer(Modifier.height(25.dp))
        PrimaryButton(
            text = stringResource(R.string.sign_up),
            onClick = {
                viewModel.onEvent(SignUpEvent.OnSignUpClick)
            },
            modifier = Modifier
                .fillMaxWidth()
        )
        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        if (state.exception.isNotBlank()) {
            Text(
                text = state.exception,
                color = Color.Red
            )
        }
    }

    LaunchedEffect(state.isRegistered) {
        if (state.isRegistered) {
            navController.navigate(Route.MainGraph.MainScreen(state.token)) {
                popUpTo(Route.AuthGraph)
            }
        }
    }
}