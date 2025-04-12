package com.example.githubpoc.cucumber.steps.viewmodel

import com.example.githubpoc.model.RepositoriesResponse
import com.example.githubpoc.model.RepositoryItem
import com.example.githubpoc.network.NetworkUtils
import com.example.githubpoc.utils.SessionManager
import com.example.githubpoc.utils.objectToJson
import com.example.githubpoc.viewmodel.RepositoriesViewModel
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull

class RepositoriesViewModelSteps {

    private lateinit var viewModel: RepositoriesViewModel
    private val mockNetworkUtils = mockk<NetworkUtils>()
    private val mockSessionManager = mockk<SessionManager>()

    @Given("User has logged in")
    fun userIsLoggedIn() {
        every { mockSessionManager.isSessionAvailable() } returns true
        viewModel = RepositoriesViewModel(mockNetworkUtils, mockSessionManager)
    }

    @Given("User is not logged in")
    fun userIsNotLoggedIn() {
        every { mockSessionManager.isSessionAvailable() } returns  false
        viewModel = RepositoriesViewModel(mockNetworkUtils, mockSessionManager)
    }

    @When("The search type is {string}")
    fun userSelectsSearchType(searchType: String) = runBlocking {
        val mockRepositoryItem = RepositoryItem(
            id = 123456,
            name = "TestRepo",
            description = "Test Description",
            htmlUrl = "https//www.baidu.com",
            url = "https//www.baidu.com"
        )
        val mockRepositoriesResponse = RepositoriesResponse(items = listOf(mockRepositoryItem))

        coEvery { mockNetworkUtils.fetchTopRepositories() } returns objectToJson(mockRepositoriesResponse)
        coEvery { mockNetworkUtils.fetchMyRepos() } returns objectToJson(mockRepositoriesResponse)

        viewModel.fetchRepositories(searchType)
        Thread.sleep(100)
    }

    @When("The search type is Language and language is {string}")
    fun userSelectsLanguage(language: String) = runBlocking {
        val mockRepositoryItem = RepositoryItem(
            id = 123456,
            name = "TestRepo",
            description = "Test Description",
            htmlUrl = "https//www.baidu.com",
            url = "https//www.baidu.com"
        )
        val mockRepositoriesResponse = RepositoriesResponse(items = listOf(mockRepositoryItem))

        coEvery { mockNetworkUtils.fetchRepositoriesByLanguage(any()) } returns objectToJson(mockRepositoriesResponse)

        viewModel.fetchRepositories("Language", language)
        Thread.sleep(100)
    }

    @Then("The error message contains {string}")
    fun searchResultShouldContainErrorMessage(errorMessage: String) = runBlocking {
        val state = viewModel.repositoriesState.first()
        assertEquals(errorMessage, state.errorMessage)
    }

    @Then("The response success")
    fun searchResultShouldContainData() = runBlocking {
        val state = viewModel.repositoriesState.first()
        assertNotNull(state.data)
    }
}
