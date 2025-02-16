package com.example.newsapp.posts.data.remote.services

import com.example.newsapp.posts.data.remote.dtos.PostDetailResponse
import com.example.newsapp.posts.data.remote.dtos.PostResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PostService {

    @GET("posts")
    suspend fun getPosts(): Response<List<PostResponse?>?>

    @GET("posts/{id}")
    suspend fun getPostDetail(
        @Path("id") id: Int
    ): Response<PostDetailResponse?>
}