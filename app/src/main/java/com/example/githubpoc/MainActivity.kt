package com.example.githubpoc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.githubpoc.navigation.HomeNavHost
import com.example.githubpoc.network.NetworkUtils
import com.example.githubpoc.utils.SessionManager

class MainActivity : ComponentActivity() {
    private val sessionManager = SessionManager()
    private val networkUtils = NetworkUtils(sessionManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SetupUI()
        }
    }

    override fun onStop() {
        if (isFinishing) {
            sessionManager.clearSession()
        }
        super.onStop()
    }

    @Composable
    private fun SetupUI() {
        MaterialTheme {
            Surface(
                color = MaterialTheme.colorScheme.background
            ) {
                val navController = rememberNavController()
                HomeNavHost(navController, sessionManager, networkUtils)
            }
        }
    }
}
