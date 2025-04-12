package com.example.githubpoc.ui.screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.testing.TestNavHostController
import com.example.githubpoc.MainActivity
import com.example.githubpoc.network.NetworkUtils
import com.example.githubpoc.utils.SessionManager
import com.example.githubpoc.viewmodel.LoginViewModel
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.spyk
import io.mockk.unmockkAll
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @MockK
    lateinit var networkUtils: NetworkUtils

    private val sessionManager = spyk(SessionManager())
    private lateinit var navController: TestNavHostController
    private lateinit var loginViewModel: LoginViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        navController = TestNavHostController(composeTestRule.activity)
        loginViewModel = spyk(LoginViewModel(sessionManager, networkUtils))
        composeTestRule.setContent {
            LoginScreen(loginViewModel = loginViewModel) {

            }
        }
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testLoginScreenInteractions() {
        composeTestRule.onNodeWithText("User Name").performTextInput("testUser")

        composeTestRule.onNodeWithText("Password").performTextInput("testPassword")

        composeTestRule.onNodeWithTag("LoginButton").performClick()

        verify { loginViewModel.onLogin("testUser", "testPassword", any()) }
    }
}
