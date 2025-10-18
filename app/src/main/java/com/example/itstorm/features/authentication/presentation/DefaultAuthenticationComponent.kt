package com.example.itstorm.features.authentication.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory

class DefaultAuthenticationComponent(
    private val storeFactory: StoreFactory,
    componentContext: ComponentContext,
    private val proceedToWeather: () -> Unit
): AuthenticationComponent, ComponentContext by componentContext {

    override fun onContinueWithoutAuthorization() = proceedToWeather()

}