package com.example.githubpoc.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.githubpoc.ui.ComposeToolbar
import com.example.githubpoc.utils.SessionManager

@Composable
fun LoginScreen(navController: NavController, sessionManager: SessionManager) {

    Scaffold(
        topBar = {
            ComposeToolbar("Login", true)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    sessionManager.sessionAvailable = true
                    navController.navigate("home_screen")
                }
            ) {
                Text("Login")
            }
        }
    }
}