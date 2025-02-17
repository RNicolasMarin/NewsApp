package com.example.newsapp.users.presentation

interface UsersAction {

    data object RetryUsers: UsersAction

    data class GoToMap(
        val lat: Double,
        val long: Double,
        val city: String
    ): UsersAction

}