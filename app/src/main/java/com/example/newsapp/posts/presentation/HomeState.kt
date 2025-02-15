package com.example.newsapp.posts.presentation

import com.example.newsapp.posts.domain.models.Post

data class HomeState(
    val posts: List<Post> = emptyList(),
    val status: HomeStatus = HomeStatus.LOADING,
)

enum class HomeStatus {
    LOADING,
    SUCCESS,
    ERROR
}
