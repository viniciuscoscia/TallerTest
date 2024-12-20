package com.vinicius.baseproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.vinicius.baseproject.ui.theme.BaseProjectTheme
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModel: MainViewModel by viewModels()
        setContent {
            BaseProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        var email by remember { mutableStateOf("") }
                        var password by remember { mutableStateOf("") }
                        val state = viewModel._mainState.collectAsStateWithLifecycle()

                        TextField(
                            value = email,
                            onValueChange = {
                                email = it
                            },
                            label = {
                                Text(text = "E-mail")
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextField(
                            value = password,
                            onValueChange = {
                                password = it
                            },
                            label = {
                                Text(text = "Password")
                            }
                        )

                        Button(onClick = {
                            lifecycleScope.launch {
                                viewModel.login(
                                    email = email,
                                    password = password
                                )
                            }
                        }) {
                            Text(text = "Login")
                        }

                        when(state.value.loginState) {
                            LoginState.Error -> {
                                Text(text = "Invalid user or password")
                            }
                            LoginState.Loading ->  {
                                CircularProgressIndicator(
                                    modifier = Modifier.width(64.dp),
                                )
                            }
                            LoginState.Success -> {
                                Text(text = "Login Successful")
                            }
                            else -> {

                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BaseProjectTheme {
        Greeting("Android")
    }
}