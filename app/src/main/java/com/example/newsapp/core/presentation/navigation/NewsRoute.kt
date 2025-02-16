package com.example.newsapp.core.presentation.navigation

import androidx.annotation.StringRes
import com.example.newsapp.R

enum class NewsRoute(val route: String, @StringRes val textId: Int) {
    HOME("Home", R.string.bottom_nav_home),
    DETAIL("Detail/{id}", R.string.bottom_nav_home),
    USERS("Users", R.string.bottom_nav_users)
}

val TOP_NAVIGATION_DESTINATIONS = listOf(
    NewsRoute.HOME, NewsRoute.USERS
)