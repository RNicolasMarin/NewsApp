package com.example.newsapp.posts.data.repositories

import com.example.newsapp.core.domain.Result
import com.example.newsapp.posts.data.mappers.toPostCompleteDomain
import com.example.newsapp.posts.data.mappers.toPostSimpleDomainList
import com.example.newsapp.posts.data.remote.services.PostService
import com.example.newsapp.posts.domain.models.PostComplete
import com.example.newsapp.posts.domain.models.PostSimple
import com.example.newsapp.posts.domain.repositories.PostRepository

class PostRepositoryImpl(
    private val service: PostService
): PostRepository {

    override suspend fun getPosts(): Result<List<PostSimple>> {
        try {
            val result = service.getPosts()
            if (!result.isSuccessful) return Result.Error

            val body = result.body().toPostSimpleDomainList() ?: return Result.Error

            return Result.Success(body)
        } catch (e: Exception) {
            return Result.Error
        }
    }

    override suspend fun getPostDetail(id: Int): Result<PostComplete?> {
        try {
            val result = service.getPostDetail(id)
            if (!result.isSuccessful) return Result.Error

            val body = result.body().toPostCompleteDomain() ?: return Result.Error

            return Result.Success(body)
        } catch (e: Exception) {
            return Result.Error
        }
    }
}