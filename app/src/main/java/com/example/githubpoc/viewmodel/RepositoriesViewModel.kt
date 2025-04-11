package com.example.githubpoc.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubpoc.model.RepositoriesResponse
import com.example.githubpoc.model.RepositoriesState
import com.example.githubpoc.model.RepositoryItem
import com.example.githubpoc.network.NetworkUtils
import com.example.githubpoc.utils.objectFromJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject

class RepositoriesViewModel(private val networkUtils: NetworkUtils) : ViewModel() {
    private val _repositoriesSate = MutableStateFlow(RepositoriesState(null, null))
    val repositoriesState = _repositoriesSate.asStateFlow()

    private val _selectedRepository = MutableStateFlow<RepositoryItem?>(null)
    val selectedRepository = _selectedRepository.asStateFlow()

    val searchType: List<String>
        get() = ArrayList<String>().apply {
            add(SearchType.TOP_10_POPULAR.value)
            add(SearchType.LANGUAGE.value)
        }

    val languageType: List<String>
        get() = ArrayList<String>().apply {
            add(LanguageType.KOTLIN.value)
            add(LanguageType.JAVA.value)
            add(LanguageType.PYTHON.value)
            add(LanguageType.LANGUAGE_C.value)
            add(LanguageType.C_PLLUS.value)
        }

    fun init() {
        fetchRepositories(SearchType.TOP_10_POPULAR.value)
    }

    fun onItemClicked(repositoryItem: RepositoryItem) {
        viewModelScope.launch {
            _selectedRepository.emit(repositoryItem)
        }
    }

    fun fetchRepositories(searchType: String, languageType: String? = null) {
        viewModelScope.launch(Dispatchers.IO) {
            _repositoriesSate.emit(RepositoriesState(null, null))
            runCatching {
                if (searchType == SearchType.LANGUAGE.value) {
                    languageType?.let {
                        networkUtils.fetchRepositoriesByLanguage(languageType)
                    }
                } else {
                    networkUtils.fetchTopRepositories()
                }
            }.onSuccess { response ->
                if (response.isNullOrEmpty()) {
                    _repositoriesSate.emit(RepositoriesState(null, "Data Error!"))
                    return@launch
                }

                runCatching {
                    objectFromJson<RepositoriesResponse>(response)
                }.onSuccess { data ->
                    if (data.items.isNullOrEmpty()) {
                        _repositoriesSate.emit(RepositoriesState(null, "Data Error!"))
                        return@launch
                    }

                    _repositoriesSate.emit(RepositoriesState(data, null))
                }.onFailure { exception ->
                    var errorMessage = exception.message ?: "Data Error!"
                    if (response.contains("message")) {
                        errorMessage = JSONObject(response).optString("message")
                    }
                    _repositoriesSate.emit(RepositoriesState(null, errorMessage))
                }
            }.onFailure { exception ->
                var errorMessage = "Request Error!"
                exception.message?.let {
                    errorMessage = it
                }
                _repositoriesSate.emit(RepositoriesState(null, errorMessage))
            }
        }
    }
}

enum class SearchType(val value: String) {
    TOP_10_POPULAR("Top 10 popular"),
    LANGUAGE("Language")
}

enum class LanguageType(val value: String) {
    KOTLIN("Kotlin"),
    JAVA("Java"),
    PYTHON("python"),
    LANGUAGE_C("C"),
    C_PLLUS("C++"),
}
