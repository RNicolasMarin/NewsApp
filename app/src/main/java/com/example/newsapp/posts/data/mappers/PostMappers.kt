package com.example.newsapp.posts.data.mappers

import com.example.newsapp.posts.data.remote.dtos.PostDetailResponse
import com.example.newsapp.posts.data.remote.dtos.PostResponse
import com.example.newsapp.posts.domain.models.PostComplete
import com.example.newsapp.posts.domain.models.PostSimple

fun List<PostResponse?>?.toPostSimpleDomainList(): List<PostSimple>? {
    if (this == null) return null

    return mapNotNull { it?.toPostSimpleDomain() }
}

fun PostResponse.toPostSimpleDomain(): PostSimple? {
    return PostSimple(
        id = id ?: return null,
        title = title ?: return null,
        content = content ?: return null,
    )
}

fun PostDetailResponse?.toPostCompleteDomain(): PostComplete? {
    if (this == null) return null

    return PostComplete(
        id = id ?: return null,
        title = title ?: return null,
        image = image ?: return null,
        status = status ?: return null,
        category = category ?: return null,
        content = content ?: return null,
        url = url ?: return null,
        publishedAt = publishedAt ?: return null,
        updatedAt = updatedAt ?: return null,
    )
}