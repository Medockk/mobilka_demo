package com.example.myapplication.app

import kotlinx.serialization.Serializable

sealed class Route {

    @Serializable
    data object AuthGraph: Route() {
        @Serializable
        data object SignIn: Route()

        @Serializable
        data object SignUp: Route()
    }

    @Serializable
    data object MainGraph: Route() {

        @Serializable
        data class MainScreen(val token: String): Route()
    }
}