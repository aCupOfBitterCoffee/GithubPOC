package com.example.githubpoc.ui.screen

import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.githubpoc.R
import com.example.githubpoc.ui.AccompanistWebViewClient
import com.example.githubpoc.ui.WebView
import com.example.githubpoc.ui.rememberWebViewNavigator
import com.example.githubpoc.ui.rememberWebViewState
import com.example.githubpoc.viewmodel.RepositoriesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepositoryScreen(
    viewModel: RepositoriesViewModel,
    navBack: () -> Unit
) {
    val repo = viewModel.selectedRepository.collectAsState().value
    val navigator = rememberWebViewNavigator()
    val state = rememberWebViewState(url = repo?.htmlUrl ?: "")

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Column {
            TopAppBar(
                title = { Text(text = repo?.name ?: "Repository") },
                navigationIcon = {
                    IconButton(onClick = {
                        if (navigator.canGoBack) {
                            navigator.navigateBack()
                        } else {
                            navBack.invoke()
                        }
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_back),
                            contentDescription = "Back"
                        )
                    }
                }
            )
            val webClient = remember {
                object : AccompanistWebViewClient() {
                    override fun onPageStarted(
                        view: WebView,
                        url: String?,
                        favicon: Bitmap?
                    ) {
                        super.onPageStarted(view, url, favicon)
                        Log.d("Accompanist WebView", "Page started loading for $url")
                    }
                }
            }

            WebView(
                state = state,
                modifier = Modifier
                    .fillMaxSize(),
                navigator = navigator,
                onCreated = { webView ->
                    webView.settings.javaScriptEnabled = true
                },
                client = webClient
            )
        }

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}
