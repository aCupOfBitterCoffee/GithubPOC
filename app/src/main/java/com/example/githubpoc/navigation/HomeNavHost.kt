package com.example.githubpoc.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.githubpoc.network.NetworkUtils
import com.example.githubpoc.ui.screen.HomeScreen
import com.example.githubpoc.ui.screen.LoginScreen
import com.example.githubpoc.ui.screen.RepositoryListScreen
import com.example.githubpoc.ui.screen.RepositoryScreen
import com.example.githubpoc.utils.SessionManager
import com.example.githubpoc.viewmodel.HomeViewModel
import com.example.githubpoc.viewmodel.LoginViewModel
import com.example.githubpoc.viewmodel.RepositoriesViewModel

@Composable
fun HomeNavHost(
    navController: NavHostController,
    sessionManager: SessionManager,
    networkUtils: NetworkUtils
) {
    val repositoriesViewModel = RepositoriesViewModel(networkUtils, sessionManager)

    NavHost(
        navController,
        startDestination = "home_screen"
    ) {
        composable("home_screen") {
            HomeScreen(
                HomeViewModel(sessionManager)
            ) { id ->
                navController.navigate(id)
            }
        }
        composable("login_screen") {
            val loginViewModel = LoginViewModel(sessionManager, networkUtils)
            LoginScreen(loginViewModel) {
                navController.navigateUp()
            }
        }
        composable("repository_list_screen") {
            RepositoryListScreen(
                viewModel = repositoriesViewModel,
                navToRepo = {
                    navController.navigate("repository_screen")
                },
                navBack = {
                    navController.navigateUp()
                }
            )
        }
        composable("repository_screen") {
            RepositoryScreen(repositoriesViewModel) {
                navController.navigateUp()
            }
        }
    }
}
