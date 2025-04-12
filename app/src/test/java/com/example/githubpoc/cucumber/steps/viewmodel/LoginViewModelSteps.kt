package com.example.githubpoc.cucumber.steps.viewmodel

import androidx.compose.runtime.collectAsState
import com.example.githubpoc.network.NetworkUtils
import com.example.githubpoc.utils.SessionManager
import com.example.githubpoc.viewmodel.LoginViewModel
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals

class LoginViewModelSteps {

    private lateinit var loginViewModel: LoginViewModel
    private val mockSessionManager = mockk<SessionManager>()
    private val mockNetworkUtils = mockk<NetworkUtils>()

    private var userName: String? = null
    private var password: String? = null
    private var token: String? = null

    @Given("a LoginViewModel instance")
    fun givenLoginViewModelInstance() {
        loginViewModel = LoginViewModel(mockSessionManager, mockNetworkUtils)
    }

    @Given("a username {string}")
    fun givenUsername(username: String) {
        this.userName = username
    }

    @Given("a password {string}")
    fun givenPassword(password: String) {
        this.password = password
    }

    @Given("a token {string}")
    fun givenToken(token: String) {
        this.token = token
    }

    @When("the user attempts to login")
    fun whenUserAttemptsToLogin() = runBlocking {
        coEvery { mockNetworkUtils.loginWithToken(any()) } answers {
            ""
        }

        loginViewModel.onLogin(userName, password, token)
    }


    @Then("the login state should have error message {string}")
    fun thenLoginStateShouldHaveErrorMessage(expectedErrorMessage: String) = runBlocking {
        val state = loginViewModel.loginState.value
        assertEquals(expectedErrorMessage, state.errorMessage)
    }

    @Then("the login API should be called")
    fun theLoginApiShouldBeCalled() = runBlocking {
        verify { mockNetworkUtils.loginWithToken(token!!) }
    }

    @Then("the login state should indicate loading {string}")
    fun thenLoginStateShouldIndicateLoading(expectedLoading: String) = runBlocking {
        val state = loginViewModel.loginState.first()
        assertEquals(expectedLoading.toBoolean(), state.loading)
    }
}
