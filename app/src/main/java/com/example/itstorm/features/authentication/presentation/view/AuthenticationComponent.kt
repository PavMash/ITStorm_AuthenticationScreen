package com.example.itstorm.features.authentication.presentation.view

import com.example.itstorm.features.authentication.presentation.store.AuthenticationStore.AuthState
import kotlinx.coroutines.flow.StateFlow

interface AuthenticationComponent {

    val model: StateFlow<AuthState>

    fun onCredentialsSubmit(login: String, password: String)

    fun onPasswordValidate(password: String)

    fun onLoginValidate(login: String)

    fun onPasswordVisibilityChange(isVisible: Boolean)

    fun onEnterWithoutAuth()
}