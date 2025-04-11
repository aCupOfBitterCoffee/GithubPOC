package com.example.githubpoc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.githubpoc.network.NetworkUtils
import com.example.githubpoc.ui.screen.HomeScreen
import com.example.githubpoc.ui.screen.LoginScreen
import com.example.githubpoc.ui.screen.RepositoryListScreen
import com.example.githubpoc.ui.screen.RepositoryScreen
import com.example.githubpoc.utils.SessionManager
import com.example.githubpoc.viewmodel.HomeViewModel
import com.example.githubpoc.viewmodel.RepositoriesViewModel

class MainActivity : ComponentActivity() {
    private val sessionManager = SessionManager()
    private val networkUtils = NetworkUtils(sessionManager)
    private val repositoriesViewModel = RepositoriesViewModel(networkUtils)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SetupUI()
        }
    }

    @Composable
    private fun SetupUI() {
        MaterialTheme {
            Surface(
                color = MaterialTheme.colorScheme.background
            ) {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "home_screen") {
                    composable("home_screen") {
                        HomeScreen(
                            navController,
                            HomeViewModel(sessionManager)
                        )
                    }
                    composable("login_screen") { LoginScreen(navController, sessionManager) }
                    composable("repository_list_screen") {
                        RepositoryListScreen(
                            navController, repositoriesViewModel
                        )
                    }
                    composable("repository_screen") { RepositoryScreen(repositoriesViewModel) }
                }
            }
        }
    }
}
