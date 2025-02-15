package com.example.newsapp.posts.data.remote.services

import com.example.newsapp.posts.data.remote.dtos.PostResponse
import retrofit2.Response
import retrofit2.http.GET

interface PostService {

    @GET("posts")
    suspend fun getPosts(): Response<List<PostResponse?>?>
}