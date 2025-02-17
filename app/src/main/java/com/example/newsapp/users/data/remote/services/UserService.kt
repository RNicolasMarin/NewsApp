package com.example.newsapp.users.data.remote.services

import com.example.newsapp.users.data.remote.dtos.UserResponse
import retrofit2.Response
import retrofit2.http.GET

interface UserService {

    @GET("users")
    suspend fun getUsers(): Response<List<UserResponse?>?>

}