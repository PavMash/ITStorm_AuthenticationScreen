package com.example.itstorm.root

import android.content.Context
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.example.itstorm.features.authentication.presentation.view.AuthenticationComponent
import com.example.itstorm.root.flow.app.AppFlowComponent
import com.example.itstorm.root.splash.SplashComponent
import kotlinx.serialization.Serializable

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>

    fun preloadNewsIfEmpty(context: Context)

    @Serializable
    sealed interface Config {
        @Serializable
        data object Authentication: Config
        @Serializable
        data object AppFlow: Config
        @Serializable
        data object Splash: Config
    }

    sealed interface Child {
        class Authentication(val component: AuthenticationComponent): Child
        class AppFlow(val component: AppFlowComponent): Child
        class Splash(val component: SplashComponent): Child
    }
}