package com.example.itstorm.features.weather.presentation.component

import com.example.itstorm.features.weather.presentation.store.WeatherStoreFactory
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.itstorm.features.weather.presentation.store.WeatherStore

class WeatherComponentImpl(
    private val storeFactory: WeatherStoreFactory,
    componentContext: ComponentContext
): WeatherComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { storeFactory.create() }

    override val model: StateFlow<WeatherStore.State> = store.stateFlow

    override fun onEstimate(city: String, temperature: Int) {
        store.accept(WeatherStore.Intent.EstimateCityWeather(city = city,
            temperature = temperature))
    }
}