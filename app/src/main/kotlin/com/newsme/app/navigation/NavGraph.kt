package com.newsme.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.newsme.app.ui.screen.*

sealed class Route(val route: String) {
    object Login : Route("login")
    object Register : Route("register")
    object Home : Route("home")
    object AddNews : Route("add_news")
    object Settings : Route("settings")
    object NewsList : Route("news_list")
}

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.Login.route
    ) {
        composable(Route.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Route.Home.route) {
                        popUpTo(Route.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Route.Register.route)
                }
            )
        }

        composable(Route.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Route.Home.route) {
                        popUpTo(Route.Register.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable(Route.Home.route) {
            HomeScreen(
                onNewsClick = { newsId ->
                    navController.navigate("${Route.NewsList.route}/$newsId")
                },
                onAddNewsClick = {
                    navController.navigate(Route.AddNews.route)
                }
            )
        }

        composable(Route.AddNews.route) {
            AddNewsScreen(
                onNewsAdded = {
                    navController.popBackStack()
                }
            )
        }

        composable(Route.Settings.route) {
            SettingsScreen()
        }
    }
}
