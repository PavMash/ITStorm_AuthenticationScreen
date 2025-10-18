package com.example.itstorm.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.itstorm.features.authentication.presentation.AuthenticationComponent
import com.example.itstorm.root.flow.app.AppFlowComponent
import kotlinx.serialization.Serializable

interface RootComponent {

    val stack: Value<ChildStack<*, Child>>
    @Serializable
    sealed interface Config {
        @Serializable
        data object Authentication: Config
        @Serializable
        data object AppFlow: Config
    }

    sealed interface Child {
        class Authentication(val component: AuthenticationComponent): Child
        class AppFlow(val component: AppFlowComponent): Child
    }
}