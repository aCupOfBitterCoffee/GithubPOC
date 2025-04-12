package com.example.githubpoc.utils

import android.content.Context
import java.io.InputStream

inline fun <reified T : Any> loadJsonFromAsserts(context: Context, fileName: String) : T? {
    try {
        val inputStream: InputStream = context.assets.open(fileName)
        val jsonString = inputStream.bufferedReader().use { it.readText() }

        return objectFromJson<T>(jsonString)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}
