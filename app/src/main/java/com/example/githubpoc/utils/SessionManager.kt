package com.example.githubpoc.utils

class SessionManager {
    private var session = Session(null)
    var sessionAvailable = false

    val token: String?
        get() = session.token

    fun isSessionAvailable() = sessionAvailable
}

data class Session(val token: String?)
