package com.example.githubpoc.cucumber.steps.viewmodel

import com.example.githubpoc.utils.SessionManager
import com.example.githubpoc.viewmodel.HomeViewModel
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert

class HomeViewModelTest {
    private lateinit var sessionManager: SessionManager
    private lateinit var homeViewModel: HomeViewModel

    @Given("User has logged in for home")
    fun userIsLoggedIn() {
        sessionManager = mockk<SessionManager>()
        every { sessionManager.isSessionAvailable() } returns true
        every { sessionManager.clearSession() } answers {
            // should not handle
        }
        homeViewModel = HomeViewModel(sessionManager)
    }

//    @Given("User is not logged in")
//    fun userIsNotLoggedIn() {
//        sessionManager = mockk<SessionManager>()
//        every { sessionManager.isSessionAvailable() } returns false
//        homeViewModel = HomeViewModel(sessionManager)
//    }

    @When("User click logout")
    fun userLogsOff() {
        homeViewModel.onLogOff()
    }

    @Then("The session will be cleared")
    fun sessionShouldBeCleared() {
        verify { sessionManager.clearSession() }
    }

    @Then("Home state will be updated")
    fun updateHomeState() {
        runBlocking {
            homeViewModel.updateState()
        }
    }

    @Then("Home page should contains {string}")
    fun homeStateShouldContain(expectedTitle: String) {
        runBlocking {
            val state = homeViewModel.homeSate.first()
            val titles = state.map { it.title }
            Assert.assertTrue(titles.contains(expectedTitle))
        }
    }
}
