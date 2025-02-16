package com.example.newsapp.posts.domain.repositories

import com.example.newsapp.core.domain.Result
import com.example.newsapp.posts.domain.models.PostComplete
import com.example.newsapp.posts.domain.models.PostSimple

interface PostRepository {

    suspend fun getPosts(): Result<List<PostSimple>>

    suspend fun getPostDetail(id: Int): Result<PostComplete?>
}