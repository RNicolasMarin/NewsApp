package com.example.newsapp.posts.data.remote.dtos

import com.google.gson.annotations.SerializedName

data class PostDetailResponse(
    @SerializedName("id")
    val id: Int? = null,
    val title: String? = null,
    val image: String? = null,
    val status: String? = null,
    val category: String? = null,
    val content: String? = null,
    val url: String? = null,
    val publishedAt: String? = null,
    val updatedAt: String? = null,
)
//Other properties on the response
/*"slug": "lorem-ipsum",
"userId": 1*/