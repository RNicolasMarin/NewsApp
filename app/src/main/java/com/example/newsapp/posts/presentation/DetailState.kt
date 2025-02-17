package com.example.newsapp.posts.presentation

import com.example.newsapp.posts.domain.models.PostComplete

data class DetailState(
    val post: PostComplete? = null,
    val status: DetailStatus = DetailStatus.LOADING,
)

enum class DetailStatus {
    LOADING,
    SUCCESS,
    ERROR
}
