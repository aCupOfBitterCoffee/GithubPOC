package com.example.githubpoc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubpoc.model.ListItem
import com.example.githubpoc.utils.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val sessionManager: SessionManager): ViewModel() {

    private val _homeState = MutableStateFlow(listOf<ListItem>())
    val homeSate = _homeState.asStateFlow()

    fun updateState() {
        viewModelScope.launch {
            val itemList = ArrayList<ListItem>()

            itemList.add(ListItem(title = "Popular Repositories", navId = "repository_list_screen"))

            if (sessionManager.isSessionAvailable()) {
                itemList.add(ListItem(title = "Log out", navId = null))
            } else {
                itemList.add(ListItem(title = "Login", navId = "login_screen"))
            }

            _homeState.emit(itemList)
        }
    }

    fun onLogOff() {
        sessionManager.clearSession()
    }
}
