package com.example.githubpoc.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RepositoriesState(
    val data: RepositoriesResponse?,
    val errorMessage: String?
)

@Serializable
data class RepositoriesResponse(
    val items: List<RepositoryItem>?
)

@Serializable
data class RepositoryItem(
    val id: Long,
    @SerialName("node_id")
    val nodeId: String? = "",
    val name: String,
    @SerialName("full_name")
    val fullName: String? = "",
    val private: Boolean? = false,
    val owner: RepositoryOwner? = null,
    @SerialName("html_url")
    val htmlUrl: String,
    val description: String? = "",
    val url: String,
    @SerialName("issues_url")
    val issuesUrl: String? = "",
    @SerialName("stargazers_count")
    val starCount: Long? = 0
)

@Serializable
data class RepositoryOwner(
    val login: String?,
    val id: Long,
    val url: String?,
    @SerialName("html_url")
    val htmlUrl: String?,
    @SerialName("repos_url")
    val reposUrl: String?
)
