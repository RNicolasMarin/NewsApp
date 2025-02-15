package com.example.newsapp.posts.domain.repositories

import com.example.newsapp.core.utils.Result
import com.example.newsapp.posts.domain.models.Post

interface PostRepository {

    suspend fun getPosts(): Result<List<Post>>
}