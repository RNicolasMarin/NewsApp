package com.example.newsapp.posts.presentation

sealed interface HomeAction {

    data object RetryInitialPosts: HomeAction
}