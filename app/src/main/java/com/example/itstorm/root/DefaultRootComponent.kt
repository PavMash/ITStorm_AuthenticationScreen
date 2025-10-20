package com.example.itstorm.root

import android.content.Context
import androidx.room.Room
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pushToFront
import com.arkivanov.decompose.value.Value
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.example.itstorm.core.data.db.AppDataBase
import com.example.itstorm.core.data.repositories.NewsRepositoryImpl
import com.example.itstorm.features.authentication.presentation.view.DefaultAuthenticationComponent
import com.example.itstorm.root.RootComponent.Config
import com.example.itstorm.root.RootComponent.Child
import com.example.itstorm.root.flow.app.DefaultAppFlowComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class DefaultRootComponent(
    componentContext: ComponentContext,
    appContext: Context
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
        initialConfiguration = Config.Authentication
    )

    private fun createChild(
        config: Config,
        childContext: ComponentContext
    ) = when(config) {
        is Config.Authentication -> Child.Authentication(
            DefaultAuthenticationComponent(
                DefaultStoreFactory(),
                childContext,
                ::proceedToWeather))
        is Config.AppFlow -> Child.AppFlow(
            DefaultAppFlowComponent(
                childContext,
                repository
            )
        )
    }

    private fun proceedToWeather() {
        navigation.pushToFront(Config.AppFlow)
    }

    override fun preloadNewsIfEmpty(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            //db.clearAllTables() // for test purposes, remove later
            repository.preloadNewsIfEmpty(context)
        }
    }
}