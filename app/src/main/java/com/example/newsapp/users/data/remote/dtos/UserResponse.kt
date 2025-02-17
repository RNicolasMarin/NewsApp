package com.example.newsapp.users.data.remote.dtos

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("firstname")
    val firstName: String? = null,
    @SerializedName("lastname")
    val lastName: String? = null,
    val email: String? = null,
    val birthDate: String? = null,
    val phone: String? = null,
    val website: String? = null,
    val address: AddressResponse? = null
)

data class AddressResponse(
    val street: String? = null,
    val suite: String? = null,
    val city: String? = null,
    val zipcode: String? = null,
    val geo: GeoResponse? = null,
)

data class GeoResponse(
    val lat: String? = null,
    val lng: String? = null,
)

/*"address":{
    "street":"123 Main Street",
    "suite":"Apt. 4",
    "city":"Anytown",
    "zipcode":"12345-6789",
    "geo":{
        "lat":"42.1234",
        "lng":"-71.2345"
    }
},*/