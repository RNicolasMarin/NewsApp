package com.example.newsapp.users.domain.models

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val birthDate: String,
    val phone: String,
    val website: String,
)