package com.example.newsapp.posts.presentation

import com.example.newsapp.posts.domain.models.Post

data class HomeState(
    val allPosts: List<Post> = emptyList(),
    val filteredPosts: List<Post> = emptyList(),
    val status: HomeStatus = HomeStatus.LOADING,
    val searching: String? = null
)

enum class HomeStatus {
    LOADING,
    SUCCESS,
    ERROR
}
