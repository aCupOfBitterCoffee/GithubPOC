package com.example.githubpoc.network

import android.util.Log
import com.example.githubpoc.utils.SessionManager
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class NetworkUtils(private val sessionManager: SessionManager) {
    private val client = OkHttpClient()

    // My Repos
    fun fetchMyRepos(): String? {
        val url = "https://api.github.com/user/repos"
        val requestBuilder = Request.Builder()
            .url(url)
            .addHeader("Accept", "application/vnd.github.v3+json")
            .addHeader("Authorization", "Bearer ${sessionManager.token}")

        val response: Response = client.newCall(requestBuilder.build()).execute()
        var result = response.body?.string()
        result?.let {
            if (!it.contains("message") && it.startsWith("[") && it.endsWith("]")) {
                result = "{\"items\":".plus(it).plus("}")
            }
        }
        result?.let {
            Log.d("fetchRepositoryDetails result:", it)
        } ?: Log.d("fetchRepositoryDetails result:", "Null")
        return result
    }

    // Login with token
    fun loginWithToken(token: String): String? {
        val url = "https://api.github.com/octocat"
        val requestBuilder = Request.Builder()
            .url(url)
            .addHeader("Accept", "application/vnd.github.v3+json")
            .addHeader("Authorization", "Bearer ${token}")
            .addHeader("X-GitHub-Api-Version", "2022-11-28")

        val response: Response = client.newCall(requestBuilder.build()).execute()
        val result = response.body?.string()
        result?.let {
            Log.d("fetchRepositoryDetails result:", it)
        } ?: Log.d("fetchRepositoryDetails result:", "Null")
        return result
    }

    // Fetch repos according to the language
    fun fetchRepositoriesByLanguage(language: String): String? {
        val url = "https://api.github.com/search/repositories?q=language:${language.lowercase()}&sort=stars&order=desc"
        val requestBuilder = Request.Builder()
            .url(url)
            .addHeader("Accept", "application/vnd.github.v3+json")

        if (sessionManager.isSessionAvailable() && !sessionManager.token.isNullOrEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer ${sessionManager.token}")
        }

        val response: Response = client.newCall(requestBuilder.build()).execute()
        val result = response.body?.string()
        result?.let {
            Log.d("fetchRepositoryDetails result:", it)
        } ?: Log.d("fetchRepositoryDetails result:", "Null")
        return result
    }

    // Hot repositories
    fun fetchTopRepositories(): String? {
        val url =
            "https://api.github.com/search/repositories?q=stars:%3E0&sort=stars&order=desc&per_page=10"
        val request = Request.Builder()
            .url(url)
            .addHeader("Accept", "application/vnd.github.v3+json")
            .build()


        val response: Response = client.newCall(request).execute()
        val result = response.body?.string()
        result?.let {
            Log.d("fetchTopRepositories result:", it)
        } ?: Log.d("fetchTopRepositories result:", "Null")
        return result
    }
}
