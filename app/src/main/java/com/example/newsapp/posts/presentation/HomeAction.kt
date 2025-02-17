package com.example.newsapp.posts.presentation

sealed interface HomeAction {

    data object RetryInitialPosts: HomeAction

    data class UpdateSearchingValue(
        val value: String
    ): HomeAction

    data object SearchingValue: HomeAction

    data class GoToDetail(
        val id: Int
    ): HomeAction
}