package com.example.newsapp.core.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsapp.core.presentation.navigation.NewsRoute
import com.example.newsapp.core.presentation.navigation.TOP_NAVIGATION_DESTINATIONS
import com.example.newsapp.posts.presentation.DetailScreenRoot
import com.example.newsapp.posts.presentation.HomeScreenRoot
import com.example.newsapp.users.presentation.UsersScreenRoot

@Composable
fun NewsApp(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route ?: NewsRoute.HOME
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        NavHost(
            navController = navController,
            startDestination = NewsRoute.HOME.route,
            modifier = Modifier.weight(0.9f)
        ) {
            composable(NewsRoute.HOME.route) {
                HomeScreenRoot(
                    goToDetail = { id ->
                        navController.navigate("Detail/$id")
                    }
                )
            }
            composable(
                NewsRoute.DETAIL.route,
                arguments = listOf(navArgument("id") { type = NavType.IntType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0
                DetailScreenRoot(id)
            }
            composable(NewsRoute.USERS.route) {
                UsersScreenRoot()
            }
        }
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route ?: NewsRoute.HOME.route
        if (currentDestination == NewsRoute.HOME.route || currentDestination == NewsRoute.USERS.route) {
            NavigationBar(modifier = Modifier.weight(0.1f).fillMaxWidth()) {
                TOP_NAVIGATION_DESTINATIONS.forEach { destination ->
                    NavigationBarItem(
                        selected = destination == currentRoute,
                        onClick = {
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Text(text = stringResource(id = destination.textId))
                        }
                    )
                }
            }
        }
    }
}