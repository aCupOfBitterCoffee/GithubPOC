package com.example.githubpoc.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.githubpoc.ui.ComposeToolbar
import com.example.githubpoc.utils.Session
import com.example.githubpoc.utils.loadJsonFromAsserts
import com.example.githubpoc.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    loginViewModel: LoginViewModel,
    backToHome: () -> Unit
) {
    val userNameState = remember { mutableStateOf("") }
    val pswState = remember { mutableStateOf("") }

    val loginState = loginViewModel.loginState.collectAsState().value

    val session = loadJsonFromAsserts<Session>(LocalContext.current, "session.json")

    Scaffold(
        topBar = {
            ComposeToolbar("Login", true)
        }
    ) {
        Box {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(it)
                    .fillMaxSize()
            ) {
                TextField(
                    value = userNameState.value,
                    onValueChange = { newText -> userNameState.value = newText },
                    label = { Text("User Name") },
                    modifier = Modifier.padding(16.dp)
                )

                TextField(
                    value = pswState.value,
                    onValueChange = { newText -> pswState.value = newText },
                    label = { Text("Password") },
                    modifier = Modifier.padding(16.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                Button(
                    onClick = {
                        loginViewModel.onLogin(userNameState.value, pswState.value, session?.token)
                    },
                    Modifier.testTag("LoginButton")
                ) {
                    Text("Login")
                }
            }
        }

        when {
            loginState.loading == true ->
                Box {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }

            !loginState.errorMessage.isNullOrEmpty() -> AlertDialog(
                modifier = Modifier.background(Color.White),
                onDismissRequest = { loginViewModel.onDialogDismiss() },
                title = {
                    Text(text = "Error")
                },
                text = {
                    Text(loginState.errorMessage)
                },
                confirmButton = {
                    Button(
                        onClick = {
                            loginViewModel.onDialogDismiss()
                        }
                    ) {
                        Text("OK")
                    }
                }
            )

            loginState.success == true -> {
                Toast.makeText(LocalContext.current, "Success!", Toast.LENGTH_SHORT).show()
                backToHome.invoke()
            }
        }
    }
}
