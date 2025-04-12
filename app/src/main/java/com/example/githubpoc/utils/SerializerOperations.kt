package com.example.githubpoc.utils

import kotlinx.serialization.json.Json

val json = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
}

inline fun <reified T: Any> objectFromJson(payload: String): T =
    json.decodeFromString<T>(payload)
