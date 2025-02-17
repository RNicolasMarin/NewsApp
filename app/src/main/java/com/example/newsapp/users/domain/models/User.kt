package com.example.newsapp.users.domain.models

data class User(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val birthDate: String,
    val phone: String,
    val website: String,
    val address: Address
)

data class Address(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: Geo,
)

data class Geo(
    val lat: Double,
    val lng: Double,
)