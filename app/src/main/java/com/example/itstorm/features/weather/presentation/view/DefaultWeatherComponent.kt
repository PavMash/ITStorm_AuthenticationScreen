package com.example.itstorm.features.weather.presentation.view

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.example.itstorm.features.weather.presentation.store.WeatherStore.Intent
import com.example.itstorm.features.weather.presentation.store.WeatherStore.Label
import com.example.itstorm.features.weather.presentation.store.WeatherStore.State
import com.example.itstorm.features.weather.presentation.store.WeatherStoreFactory
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultWeatherComponent(
    private val storeFactory: StoreFactory,
    componentContext: ComponentContext,
    onNewsClicked: () -> Unit,
    onFavoritesClicked: () -> Unit
): WeatherComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { WeatherStoreFactory(storeFactory).create() }
    private val scope = coroutineScope()

    init {
        scope.launch {
            store.labels.collect {
                when(it) {
                    Label.ClickNews -> {
                        onNewsClicked()
                    }
                    Label.ClickFavorites -> {
                        onFavoritesClicked()
                    }
                }
            }
        }
    }

    override val model: StateFlow<State> = store.stateFlow

    override fun onEstimate(city: String, temperature: Int) {
        store.accept(
            Intent.EstimateCityWeather(city = city,
            temperature = temperature))
    }

    override fun onRetryEstimation() {
        store.accept(Intent.RetryEstimation)
    }

    override fun onCityValidate(city: String) {
        store.accept(Intent.ValidateCityName(city))
    }

    override fun onTemperatureValidate(temperature: String) {
        store.accept(Intent.ValidateTemperature(temperature))
    }

    override fun onNewsClicked() {
        store.accept(Intent.ClickNews)
    }

    override fun onFavoritesClicked() {
        store.accept(Intent.ClickFavorites)
    }
}