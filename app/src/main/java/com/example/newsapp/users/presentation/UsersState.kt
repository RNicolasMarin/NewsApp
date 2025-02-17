package com.example.newsapp.users.presentation

import com.example.newsapp.users.domain.models.User

data class UsersState(
    val users: List<User> = emptyList(),
    val status: UsersStatus = UsersStatus.LOADING,
)

enum class UsersStatus {
    LOADING,
    SUCCESS,
    ERROR
}