package com.example.itstorm.features.authentication.presentation.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.itstorm.features.authentication.domain.User
import com.example.itstorm.features.authentication.domain.ValidationResult
import com.example.itstorm.features.authentication.presentation.store.AuthenticationStore.Intent
import com.example.itstorm.features.authentication.presentation.store.AuthenticationStore.AuthState
import com.example.itstorm.features.authentication.presentation.store.AuthenticationStore.Label

class AuthenticationStoreFactory(private val storeFactory: StoreFactory) {

    fun create(): AuthenticationStore =
        object:  AuthenticationStore, Store<Intent, AuthState, Label> by storeFactory.create(
            name = "WeatherStore",
            initialState = AuthState(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::AuthenticationExecutor,
            reducer = AuthenticationReducer
        ) {}

    private sealed interface Msg {
        data class CredentialsCaptured(val user: User) : Msg
        data class LoginValidated(val newLogin: String,
                                     val validationRes: ValidationResult): Msg
        data class PasswordValidated(val newPassword: String,
                                     val validationRes: ValidationResult): Msg
        data class PasswordVisibilityChanged(val isVisible: Boolean): Msg
    }

    private class AuthenticationExecutor: CoroutineExecutor<Intent, Unit, AuthState, Msg, Label>() {
        override fun executeIntent(intent: Intent) =
            when(intent) {
                is Intent.SubmitLoginCredentials ->
                    submitCredentials(intent.login,
                        intent.password)
                is Intent.ValidatePassword ->
                    validatePassword(intent.password)
                is Intent.ValidateLogin ->
                    validateLogin(intent.login)
                is Intent.ChangePasswordVisibility ->
                    dispatch(Msg.PasswordVisibilityChanged(intent.isVisible))
                is Intent.EnterWithoutAuth ->
                    publish(Label.EnterWithoutAuth)
            }

        private fun submitCredentials(username: String, password: String) {
            dispatch(Msg.CredentialsCaptured(User(username, password)))
        }

        private fun validatePassword(password: String) {
            val latinRegex = Regex("^(?=.*[A-Za-z]).+$")
            val digitRegex = Regex("^(?=.*\\d).+$")

            when {
                (!latinRegex.matches(password)) ->
                    dispatch(Msg.PasswordValidated(
                        newPassword = password,
                        validationRes = ValidationResult.NoLatinLettersInPw))

                (!digitRegex.matches(password)) ->
                    dispatch(Msg.PasswordValidated(
                        newPassword = password,
                        validationRes = ValidationResult.NoDigitsInPw))

                (password.length < 6) ->
                    dispatch(Msg.PasswordValidated(
                        newPassword = password,
                        validationRes = ValidationResult.ShortPw))

                else -> dispatch(Msg.PasswordValidated(
                    newPassword = password,
                    validationRes = ValidationResult.Valid))
            }
        }

        private fun validateLogin(login: String) {
            val approvedLogin = "Логин_Юзера"
            val cyrillicRegex = Regex("^[А-Яа-яЁё_]+$")

            when {
                (!cyrillicRegex.matches(login)) ->
                    dispatch(Msg.LoginValidated(
                        newLogin = login,
                        validationRes = ValidationResult.NonCyrillicLogin))

                (login != approvedLogin) ->
                    dispatch(Msg.LoginValidated(
                        newLogin = login,
                        validationRes = ValidationResult.WrongLogin))

                else -> dispatch(Msg.LoginValidated(
                    newLogin = login,
                    validationRes = ValidationResult.Valid))
            }
        }
    }

    private object AuthenticationReducer: Reducer<AuthState, Msg> {
        override fun AuthState.reduce(msg: Msg): AuthState =
            when (msg) {
                is Msg.CredentialsCaptured ->
                    copy(
                        user = msg.user,
                        passwordErrMessage = passwordErrMessage,
                        loginErrMessage = loginErrMessage,
                        isPasswordVisible = isPasswordVisible
                    )

                is Msg.LoginValidated ->
                    copy(
                        user = user.copy(
                            login = msg.newLogin,
                            password = user.password
                        ),
                        passwordErrMessage = passwordErrMessage,
                        loginErrMessage = msg.validationRes,
                        isPasswordVisible = isPasswordVisible
                    )

                is Msg.PasswordValidated ->
                    copy(
                        user = user.copy(
                            login = user.login,
                            password = msg.newPassword
                        ),
                        passwordErrMessage = msg.validationRes,
                        loginErrMessage = loginErrMessage,
                        isPasswordVisible = isPasswordVisible
                    )

                is Msg.PasswordVisibilityChanged -> {
                    copy(
                        user = user,
                        passwordErrMessage = passwordErrMessage,
                        loginErrMessage = loginErrMessage,
                        isPasswordVisible = msg.isVisible
                    )
                }

            }
    }
}