package com.example.githubpoc.ui.screen

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import androidx.test.platform.app.InstrumentationRegistry
import com.example.githubpoc.model.RepositoriesResponse
import com.example.githubpoc.model.RepositoriesState
import com.example.githubpoc.model.RepositoryItem
import com.example.githubpoc.network.NetworkUtils
import com.example.githubpoc.utils.SessionManager
import com.example.githubpoc.viewmodel.RepositoriesViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RepositoryListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    @MockK
    lateinit var networkUtils: NetworkUtils

    private val sessionManager = spyk(SessionManager())
    private lateinit var repositoriesViewModel: RepositoriesViewModel

    @Before
    fun testSearchButtonClick() {
        MockKAnnotations.init(this)
        repositoriesViewModel = spyk(RepositoriesViewModel(networkUtils, sessionManager))
    }

    @After
    fun tearDown() {
        unmockkAll()
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            navController.popBackStack()
        }
    }

    @Test
    fun testRepositoryItemClick() {
        val mockRepositoryItem = RepositoryItem(
            id = 123456,
            name = "TestRepo",
            description = "Test Description",
            htmlUrl = "https//www.baidu.com",
            url = "https//www.baidu.com"
        )
        val mockRepositoriesResponse = RepositoriesResponse(items = listOf(mockRepositoryItem))
        val state = RepositoriesState(mockRepositoriesResponse, null)

        every { repositoriesViewModel.repositoriesState } returns MutableStateFlow(state).asStateFlow()

        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(
                ComposeNavigator()
            )
            MaterialTheme {
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    RepositoryListScreen(
                        repositoriesViewModel,
                        navToRepo = {},
                        navBack = {}
                    )
                }
            }
        }

        composeTestRule.onNodeWithText("Test Description").performClick()

        verify { repositoriesViewModel.onItemClicked(mockRepositoryItem) }
    }
}
