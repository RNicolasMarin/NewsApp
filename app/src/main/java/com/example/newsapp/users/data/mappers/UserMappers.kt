package com.example.newsapp.users.data.mappers

import com.example.newsapp.users.data.remote.dtos.AddressResponse
import com.example.newsapp.users.data.remote.dtos.GeoResponse
import com.example.newsapp.users.data.remote.dtos.UserResponse
import com.example.newsapp.users.domain.models.Address
import com.example.newsapp.users.domain.models.Geo
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
        address = address?.toAddress() ?: return null,
    )
}

fun AddressResponse.toAddress(): Address? {
    return Address(
        street = street ?: return null,
        suite = suite ?: return null,
        city = city ?: return null,
        zipcode = zipcode ?: return null,
        geo = geo?.toGeo() ?: return null,
    )
}

fun GeoResponse.toGeo(): Geo? {
    return Geo(
        lat = lat?.toDoubleOrNull() ?: return null,
        lng = lng?.toDoubleOrNull() ?: return null,
    )
}