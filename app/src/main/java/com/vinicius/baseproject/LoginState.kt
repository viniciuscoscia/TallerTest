package com.vinicius.baseproject

class MainState(
    val loginState: LoginState = LoginState.Default
)

sealed interface LoginState {
    data object Loading: LoginState
    data object Success: LoginState
    data object Error: LoginState
    data object Default: LoginState
}