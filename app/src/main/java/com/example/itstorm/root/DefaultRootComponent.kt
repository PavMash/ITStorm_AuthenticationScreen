package com.example.itstorm.root

import android.content.Context
import androidx.room.Room
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pushToFront
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.example.itstorm.core.data.db.AppDataBase
import com.example.itstorm.core.data.repositories.NewsRepositoryImpl
import com.example.itstorm.features.authentication.presentation.view.DefaultAuthenticationComponent
import com.example.itstorm.root.RootComponent.Config
import com.example.itstorm.root.RootComponent.Child
import com.example.itstorm.root.flow.app.DefaultAppFlowComponent
import com.example.itstorm.root.splash.DefaultSplashComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class DefaultRootComponent(
    componentContext: ComponentContext,
    private val appContext: Context
): RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    private val db = Room.databaseBuilder(
        appContext,
        AppDataBase::class.java,
        "database"
    ).fallbackToDestructiveMigration(false).build()
    private val repository = NewsRepositoryImpl(db.newsDao())

    override val stack: Value<ChildStack<*, Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        childFactory = ::createChild,
        handleBackButton = true,
        initialConfiguration = Config.Splash
    )

    private fun createChild(
        config: Config,
        childContext: ComponentContext
    ) = when(config) {
        is Config.Authentication -> Child.Authentication(
            component = DefaultAuthenticationComponent(
                storeFactory = DefaultStoreFactory(),
                componentContext = childContext,
                proceedToWeather = ::proceedToWeather))
        is Config.AppFlow -> Child.AppFlow(
            component = DefaultAppFlowComponent(
                componentContext = childContext,
                repository
            )
        )
        is Config.Splash -> Child.Splash(
            component = DefaultSplashComponent(
                onFinished = ::onSplashFinished,
                preloadAppResources = { preloadNewsIfEmpty(appContext) },
                componentContext = childContext
            )
        )
    }

    private fun proceedToWeather() {
        navigation.pushToFront(Config.AppFlow)
    }

    private fun onSplashFinished() {
        navigation.replaceCurrent(Config.Authentication)
    }

    override fun preloadNewsIfEmpty(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.preloadNewsIfEmpty(context)
        }
    }
}