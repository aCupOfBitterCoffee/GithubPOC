package com.example.githubpoc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubpoc.network.NetworkUtils
import com.example.githubpoc.utils.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class LoginViewModel(
    private val sessionManager: SessionManager,
    private val networkUtils: NetworkUtils
) : ViewModel() {
    private val _loginState = MutableStateFlow(LoginState(null, null, null))
    val loginState = _loginState.asStateFlow()

    fun onDialogDismiss() {
        viewModelScope.launch{
            _loginState.emit(LoginState(null, null, null))
        }
    }

    fun onLogin(userName: String?, psw: String?, token: String?) {
        if (userName.isNullOrEmpty()) {
            viewModelScope.launch{
                _loginState.emit(LoginState(null, "Please input username", null))
            }
            return
        }

        if (psw.isNullOrEmpty()) {
            viewModelScope.launch{
                _loginState.emit(LoginState(null, "Please input password", null))
            }
            return
        }

        if (token.isNullOrEmpty()) {
            viewModelScope.launch{
                _loginState.emit(LoginState(null, "Get token failed, please restart the app", null))
            }
            return
        }

        if (userName == "aCupOfBitterCoffee" && psw == "Abc@2025") {
            login(userName, token)
        } else {
            viewModelScope.launch{
                _loginState.emit(LoginState(null, "The username or password is incorrect, please re-enterp", null))
            }
        }
    }

    private fun login(userName: String, token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _loginState.emit(LoginState(true, null, null))
            runCatching {
                networkUtils.loginWithToken(token)
            }.onSuccess { response ->
                if (response == null) {
                    _loginState.emit(LoginState(false, "Login failed", null))
                    return@launch
                }

                if (response.contains("message")) {
                    val errorMessage = JSONObject(response).optString("message")
                    _loginState.emit(LoginState(false, errorMessage, null))
                    return@launch
                }

                sessionManager.setSession(token, userName)
                _loginState.emit(LoginState(false, null, true))
            }.onFailure { exception ->
                val errorMessage = exception.message ?: "Login failed"
                _loginState.emit(LoginState(false, errorMessage, null))
            }
        }
    }
}

data class LoginState(
    val loading: Boolean?,
    val errorMessage: String?,
    val success: Boolean?
)
