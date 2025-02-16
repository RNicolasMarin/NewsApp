package com.example.newsapp.posts.domain.models

data class PostComplete(
    val id: Int,
    val title: String,
    val image: String,
    val status: String,
    val category: String,
    val content: String,
    val url: String,
    val publishedAt: String,
    val updatedAt: String,
)
