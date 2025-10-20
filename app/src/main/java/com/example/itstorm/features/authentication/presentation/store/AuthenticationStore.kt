package com.example.itstorm.features.authentication.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.example.itstorm.features.authentication.domain.User
import com.example.itstorm.features.authentication.domain.ValidationResult
import com.example.itstorm.features.authentication.presentation.store.AuthenticationStore.Intent
import com.example.itstorm.features.authentication.presentation.store.AuthenticationStore.Label
import com.example.itstorm.features.authentication.presentation.store.AuthenticationStore.AuthState

interface AuthenticationStore: Store<Intent, AuthState, Label> {

    sealed interface Intent {
        data class ValidateLogin(val login: String): Intent
        data class ValidatePassword(val password: String): Intent
        data class SubmitLoginCredentials(val login: String,
                                          val password: String): Intent
        data class ChangePasswordVisibility(val isVisible: Boolean): Intent
        data object EnterWithoutAuth: Intent
    }

    data class AuthState(
        val user: User = User(),
        val passwordErrMessage: ValidationResult = ValidationResult.Valid,
        val loginErrMessage: ValidationResult = ValidationResult.Valid,
        val isPasswordVisible: Boolean = false
    )

    sealed interface Label {
        data object EnterWithoutAuth: Label
    }
}