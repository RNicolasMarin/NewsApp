package com.example.newsapp.posts.presentation

import com.example.newsapp.posts.domain.models.PostSimple

data class HomeState(
    val allPosts: List<PostSimple> = emptyList(),
    val filteredPosts: List<PostSimple> = emptyList(),
    val status: HomeStatus = HomeStatus.LOADING,
    val searching: String? = null
)

enum class HomeStatus {
    LOADING,
    SUCCESS,
    ERROR
}
