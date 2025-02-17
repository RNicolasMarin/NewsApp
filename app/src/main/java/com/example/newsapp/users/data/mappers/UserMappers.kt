package com.example.newsapp.users.data.mappers

import com.example.newsapp.users.data.remote.dtos.UserResponse
import com.example.newsapp.users.domain.models.User

fun List<UserResponse?>?.toUserDomainList(): List<User>? {
    if (this == null) return null

    return mapNotNull { it?.toUserDomain() }
}

fun UserResponse.toUserDomain(): User? {
    return User(
        id = id ?: return null,
        firstName = firstName ?: return null,
        lastName = lastName ?: return null,
        email = email ?: return null,
        birthDate = birthDate ?: return null,
        phone = phone ?: return null,
        website = website ?: return null,
    )
}