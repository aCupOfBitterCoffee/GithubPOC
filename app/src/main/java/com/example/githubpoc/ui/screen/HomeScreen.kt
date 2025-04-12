package com.example.githubpoc.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.githubpoc.ui.ComposeToolbar
import com.example.githubpoc.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    navToTargetScreen: (id:String) -> Unit
) {
    homeViewModel.updateState()
    val items = homeViewModel.homeSate.collectAsState().value
    val showDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            ComposeToolbar("Home")
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

            items.forEach { item ->
                Item(item.title) {
                    item.navId?.let {
                        navToTargetScreen.invoke(it)
                    }

                    if (item.title == "Log out") {
                        showDialog.value = true
                    }
                }
            }
        }
    }

    if (showDialog.value) {
        AlertDialog(
            modifier = Modifier.background(Color.White),
            onDismissRequest = { showDialog.value = false },
            title = {
                Text(text = "Tips")
            },
            text = {
                Text("Are you sure log out?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        homeViewModel.onLogOff()
                        homeViewModel.updateState()
                        showDialog.value = false
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
private fun Item(title: String, clickListener: () -> Unit) {
    Button(
        onClick = clickListener
    ) {
        Text(title)
    }
}
