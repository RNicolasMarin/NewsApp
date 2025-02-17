package com.example.newsapp.users.domain.repositories

import com.example.newsapp.core.domain.Result
import com.example.newsapp.users.domain.models.User

interface UserRepository {

    suspend fun getUsers(): Result<List<User>>

}