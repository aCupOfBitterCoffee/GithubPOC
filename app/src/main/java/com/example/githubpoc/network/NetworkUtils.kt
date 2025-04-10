package com.example.githubpoc.network

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

class NetworkUtils {
    private val client = OkHttpClient()

    private fun makeNetworkRequest(url: String): String? {
        val request = Request.Builder()
            .url(url)
            .build()

        return try {
            val response: Response = client.newCall(request).execute()
            response.body?.string()
        } catch (e: Exception) {
            null
        }
    }

    // Hot repositories
    private fun fetchTopRepositories(): String? {
        val url = "https://api.github.com/search/repositories?q=stars:%3E0&sort=stars&order=desc&per_page=10"
        val request = Request.Builder()
            .url(url)
            .addHeader("Accept", "application/vnd.github.v3+json")
            .build()

        return try {
            val response: Response = client.newCall(request).execute()
            response.body?.string()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
