package com.example.newsapp.users.data.repositories

import com.example.newsapp.core.domain.Result
import com.example.newsapp.users.data.mappers.toUserDomainList
import com.example.newsapp.users.data.remote.services.UserService
import com.example.newsapp.users.domain.models.User
import com.example.newsapp.users.domain.repositories.UserRepository

class UserRepositoryImpl(
    private val service: UserService
): UserRepository {

    override suspend fun getUsers(): Result<List<User>> {
        try {
            val result = service.getUsers()
            if (!result.isSuccessful) return Result.Error

            val body = result.body().toUserDomainList() ?: return Result.Error

            return Result.Success(body)
        } catch (e: Exception) {
            return Result.Error
        }
    }
}