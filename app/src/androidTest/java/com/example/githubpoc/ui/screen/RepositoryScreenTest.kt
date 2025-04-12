package com.example.githubpoc.ui.screen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.githubpoc.model.RepositoryItem
import com.example.githubpoc.viewmodel.RepositoriesViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class RepositoryScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @MockK
    lateinit var mockViewModel: RepositoriesViewModel

    @Before
    fun testSearchButtonClick() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun testWebViewLoadsUrl() {
        val repo = RepositoryItem(
            id = 123456,
            name = "Test Repository",
            fullName = "Test/Test Repository",
            description = "Test Description",
            htmlUrl = "https://github.com",
            url = "https://github.com"
        )

        every { mockViewModel.selectedRepository } returns MutableStateFlow(repo).asStateFlow()

        // Set content
        composeTestRule.setContent {
            RepositoryScreen(viewModel = mockViewModel) {

            }
        }

        composeTestRule.onNodeWithText("Test Repository").assertExists()
        composeTestRule.onNodeWithContentDescription("Back").assertExists()
    }
}
