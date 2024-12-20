package com.vinicius.baseproject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {
    private val mainState = MutableStateFlow(MainState())
    val _mainState = mainState.asStateFlow()

    suspend fun login(
        email: String,
        password: String
    ) {
        mainState.update {
            MainState(loginState = LoginState.Loading)
        }

        delay(3000)

        if (email == "vncoscia@gmail.com" && password == "abcd1234") {
            mainState.update {
                MainState(loginState = LoginState.Success)
            }
        } else {
            mainState.update {
                MainState(loginState = LoginState.Error)
            }
        }
    }
}