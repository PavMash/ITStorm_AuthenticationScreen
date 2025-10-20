package com.example.itstorm.features.authentication.presentation.view

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.example.itstorm.features.authentication.presentation.store.AuthenticationStore.AuthState
import com.example.itstorm.features.authentication.presentation.store.AuthenticationStore.Label
import com.example.itstorm.features.authentication.presentation.store.AuthenticationStore.Intent
import com.example.itstorm.features.authentication.presentation.store.AuthenticationStoreFactory
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultAuthenticationComponent(
    private val storeFactory: StoreFactory,
    componentContext: ComponentContext,
    private val proceedToWeather: () -> Unit
): AuthenticationComponent, ComponentContext by componentContext {

    val store = instanceKeeper.getStore {
        AuthenticationStoreFactory(storeFactory).create()
    }

    private val scope = coroutineScope()

    init {
        scope.launch {
            store.labels.collect {
                when(it) {
                    is Label.EnterWithoutAuth -> {
                        proceedToWeather()
                    }
                }
            }
        }
    }

    override val model: StateFlow<AuthState> = store.stateFlow

    override fun onCredentialsSubmit(login: String, password: String) {
        store.accept(Intent.SubmitLoginCredentials(login, password))
    }

    override fun onPasswordValidate(password: String) {
        store.accept(Intent.ValidatePassword(password))
    }

    override fun onLoginValidate(login: String) {
        store.accept(Intent.ValidateLogin(login))
    }

    override fun onPasswordVisibilityChange(isVisible: Boolean) {
        store.accept(Intent.ChangePasswordVisibility(isVisible))
    }

    override fun onEnterWithoutAuth() {
        store.accept(Intent.EnterWithoutAuth)
    }

}