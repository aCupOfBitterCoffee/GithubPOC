package com.example.githubpoc.ui.screen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import com.example.githubpoc.model.RepositoriesResponse
import com.example.githubpoc.model.RepositoryItem
import com.example.githubpoc.viewmodel.RepositoriesViewModel
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.*

class RepositoryListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockNavController = mock(NavController::class.java)
    private val mockViewModel = mock(RepositoriesViewModel::class.java)

    @Test
    fun testSearchButtonClick() {
        composeTestRule.setContent {
            RepositoryListScreen(navController = mockNavController, viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithText("Search").performClick()

        verify(mockViewModel).fetchRepositories(anyString(), anyString())
    }

    @Test
    fun testRepositoryItemClick() {
        val mockRepositoryItem = RepositoryItem(
            name = "TestRepo",
            description = "Test Description",
            starCount = 100
        )
        val mockRepositoriesResponse = RepositoriesResponse(items = listOf(mockRepositoryItem))

        `when`(mockViewModel.repositoriesState).thenReturn(
            mutableStateOf(
                RepositoriesViewModel.RepositoriesState(data = mockRepositoriesResponse)
            )
        )

        composeTestRule.setContent {
            RepositoryListScreen(navController = mockNavController, viewModel = mockViewModel)
        }

        composeTestRule.onNodeWithText("TestRepo").performClick()

        verify(mockNavController).navigate("repository_screen")
        verify(mockViewModel).onItemClicked(mockRepositoryItem)
    }
}
