package com.example.githubpoc.ui.screen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.githubpoc.viewmodel.HomeViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testNavigationAndDialog() {
        val navController = TestNavHostController(composeTestRule.activity)
        val homeViewModel = HomeViewModel() // Mock or provide a test instance of HomeViewModel

        composeTestRule.setContent {
            HomeScreen(navController = navController, homeViewModel = homeViewModel)
        }

        // Test navigation button click
        composeTestRule.onNodeWithText("Some Item Title") // Replace with an actual item title
            .performClick()
        // Assert navigation logic here (e.g., check navController.currentDestination)

        // Test "Log out" button click and dialog appearance
        composeTestRule.onNodeWithText("Log out")
            .performClick()
        composeTestRule.onNodeWithText("Are you sure log out?")
            .assertExists()

        // Test dialog confirm button
        composeTestRule.onNodeWithText("Confirm")
            .performClick()
        // Assert dialog is dismissed and log out logic is triggered

        // Test dialog cancel button
        composeTestRule.onNodeWithText("Log out")
            .performClick()
        composeTestRule.onNodeWithText("Cancel")
            .performClick()
        composeTestRule.onNodeWithText("Are you sure log out?")
            .assertDoesNotExist()
    }
}
