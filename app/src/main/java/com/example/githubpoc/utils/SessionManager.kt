package com.example.githubpoc.utils

import kotlinx.serialization.Serializable

class SessionManager {
    private var session: Session? = null

    val token: String?
        get() = session?.token

    val userName: String?
        get() = session?.userName

    fun isSessionAvailable() = session?.token != null

    fun setSession(token: String?, userName: String?) {
        session = Session(token, userName)
    }

    fun clearSession() {
        session = null
    }
}

@Serializable
data class Session(val token: String?, val userName: String?)
