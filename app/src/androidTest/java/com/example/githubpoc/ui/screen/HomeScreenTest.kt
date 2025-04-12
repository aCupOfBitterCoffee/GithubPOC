package com.example.githubpoc.ui.screen

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.githubpoc.navigation.HomeNavHost
import com.example.githubpoc.network.NetworkUtils
import com.example.githubpoc.utils.SessionManager
import com.example.githubpoc.viewmodel.HomeViewModel
import io.mockk.spyk
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    private val sessionManager = spyk(SessionManager())
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var networkUtils: NetworkUtils

    @Before
    fun setup() {
        homeViewModel = HomeViewModel(sessionManager)
        networkUtils = NetworkUtils(sessionManager)
    }

    @After
    fun tearDown() {
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            navController.popBackStack()
        }
    }

    @Test
    fun testNavigateToRepos() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(
                ComposeNavigator()
            )
            MaterialTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeNavHost(navController, sessionManager, networkUtils)
                }
            }
        }
        composeTestRule.onNodeWithText("Popular Repositories").performClick()

        composeTestRule.onNodeWithText("Repository List").assertExists()
    }

    @Test
    fun testNavigateToLogin() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(
                ComposeNavigator()
            )
            MaterialTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeNavHost(navController, sessionManager, networkUtils)
                }
            }
        }
        composeTestRule.onNodeWithText("Login").performClick()

        composeTestRule.onNodeWithText("User Name").assertExists()
    }

    @Test
    fun testLogoutWithConfirm() {
        sessionManager.setSession("aa", "bb")

        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(
                ComposeNavigator()
            )
            MaterialTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeNavHost(navController, sessionManager, networkUtils)
                }
            }
        }

        composeTestRule.onNodeWithText("Log out").performClick()
        composeTestRule.onNodeWithText("Are you sure log out?").assertExists()

        composeTestRule.onNodeWithText("Confirm").performClick()
        composeTestRule.onNodeWithText("Are you sure log out?").assertDoesNotExist()
        composeTestRule.onNodeWithText("Log out").assertDoesNotExist()
        composeTestRule.onNodeWithText("Login").assertExists()
    }

    @Test
    fun testLogoutWithCancel() {
        sessionManager.setSession("aa", "bb")

        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(
                ComposeNavigator()
            )
            MaterialTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    HomeNavHost(navController, sessionManager, networkUtils)
                }
            }
        }

        composeTestRule.onNodeWithText("Log out").performClick()
        composeTestRule.onNodeWithText("Are you sure log out?").assertExists()

        composeTestRule.onNodeWithText("Cancel").performClick()
        composeTestRule.onNodeWithText("Are you sure log out?").assertDoesNotExist()
        composeTestRule.onNodeWithText("Log out").assertExists()
        composeTestRule.onNodeWithText("Login").assertDoesNotExist()
    }
}
