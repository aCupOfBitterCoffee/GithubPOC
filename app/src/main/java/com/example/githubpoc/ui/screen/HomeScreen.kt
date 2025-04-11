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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.githubpoc.ui.ComposeToolbar
import com.example.githubpoc.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel
) {
    homeViewModel.updateState()
    val items = homeViewModel.homeSate.collectAsState().value

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
                        navController.navigate(it)
                    }
                }
            }
        }
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
