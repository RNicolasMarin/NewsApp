package com.example.newsapp.posts.data.repositories

import com.example.newsapp.core.utils.Result
import com.example.newsapp.posts.data.mappers.toPostDomainList
import com.example.newsapp.posts.data.remote.services.PostService
import com.example.newsapp.posts.domain.models.Post
import com.example.newsapp.posts.domain.repositories.PostRepository

class PostRepositoryImpl(
    private val service: PostService
): PostRepository {

    override suspend fun getPosts(): Result<List<Post>> {
        try {
            val result = service.getPosts()
            if (!result.isSuccessful) return Result.Error

            val body = result.body().toPostDomainList() ?: return Result.Error

            return Result.Success(body)
        } catch (e: Exception) {
            return Result.Error
        }
    }
}