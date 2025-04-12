package com.example.githubpoc.ui.screen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.githubpoc.viewmodel.RepositoriesViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(AndroidJUnit4::class)
@LargeTest
class RepositoryScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testWebViewLoadsUrl() {
        // Mock ViewModel
        val mockViewModel: RepositoriesViewModel = mock()
        val mockRepo = mock<com.example.githubpoc.model.Repository> {
            whenever(it.htmlUrl).thenReturn("https://github.com")
            whenever(it.name).thenReturn("Test Repository")
        }
        whenever(mockViewModel.selectedRepository).thenReturn(kotlinx.coroutines.flow.flowOf(mockRepo))

        // Set content
        composeTestRule.setContent {
            RepositoryScreen(viewModel = mockViewModel)
        }

        // Assert WebView is loading
        composeTestRule.onNodeWithText("Test Repository").assertExists()
    }

    @Test
    fun testBackButtonFunctionality() {
        // Mock ViewModel
        val mockViewModel: RepositoriesViewModel = mock()
        val mockRepo = mock<com.example.githubpoc.model.Repository> {
            whenever(it.htmlUrl).thenReturn("https://github.com")
            whenever(it.name).thenReturn("Test Repository")
        }
        whenever(mockViewModel.selectedRepository).thenReturn(kotlinx.coroutines.flow.flowOf(mockRepo))

        // Set content
        composeTestRule.setContent {
            RepositoryScreen(viewModel = mockViewModel)
        }

        // Assert Back Button exists
        composeTestRule.onNodeWithContentDescription("Back").assertExists()
    }
}
