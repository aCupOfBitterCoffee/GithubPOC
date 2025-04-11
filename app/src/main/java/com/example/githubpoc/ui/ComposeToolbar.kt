package com.example.githubpoc.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.githubpoc.R
import com.example.githubpoc.utils.findActivity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComposeToolbar(
    title: String,
    canGoBack: Boolean = false,
    onBack: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val activity = context.findActivity()

    TopAppBar(
        title = { Text(title) },
        colors = TopAppBarDefaults.topAppBarColors(),
        navigationIcon = {
            if (canGoBack) {
                IconButton(
                    onClick = {
                        onBack?.invoke() ?: activity?.onBackPressedDispatcher?.onBackPressed()
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_back),
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}