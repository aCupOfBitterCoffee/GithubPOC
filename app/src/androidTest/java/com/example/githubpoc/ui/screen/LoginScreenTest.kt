package com.example.githubpoc.ui.screen

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.NavController
import com.example.githubpoc.viewmodel.LoginViewModel
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockNavController = Mockito.mock(NavController::class.java)
    private val mockLoginViewModel = Mockito.mock(LoginViewModel::class.java)

    @Test
    fun testLoginScreenInteractions() {
        composeTestRule.setContent {
            LoginScreen(navController = mockNavController, loginViewModel = mockLoginViewModel)
        }

        // 输入用户名
        composeTestRule.onNodeWithText("User Name").performTextInput("testUser")

        // 输入密码
        composeTestRule.onNodeWithText("Password").performTextInput("testPassword")

        // 点击登录按钮
        composeTestRule.onNodeWithText("Login").performClick()

        // 验证 ViewModel 的登录方法被调用
        Mockito.verify(mockLoginViewModel).onLogin("testUser", "testPassword", null)
    }
}
