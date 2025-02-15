package com.example.newsapp.posts.data.remote.dtos

import com.google.gson.annotations.SerializedName

data class PostResponse(
    @SerializedName("id")
    val id: Int? = null,
    val title: String? = null,
    val content: String? = null,
)
