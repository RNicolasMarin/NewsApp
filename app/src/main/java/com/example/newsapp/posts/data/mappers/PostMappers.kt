package com.example.newsapp.posts.data.mappers

import com.example.newsapp.posts.data.remote.dtos.PostResponse
import com.example.newsapp.posts.domain.models.Post

fun List<PostResponse?>?.toPostDomainList(): List<Post>? {
    if (this == null) return null

    return mapNotNull { it?.toPostDomain() }
}

fun PostResponse.toPostDomain(): Post? {
    return Post(
        id = id ?: return null,
        title = title ?: return null,
        content = content ?: return null,
    )
}