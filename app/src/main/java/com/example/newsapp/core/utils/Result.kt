package com.example.newsapp.core.utils

sealed class Result<out T> {

    data class Success<out T>(val data: T): Result<T>()
    data object Error: Result<Nothing>()

}