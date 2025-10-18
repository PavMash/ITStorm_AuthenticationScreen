package com.example.itstorm.features.weather.presentation.view

import com.example.itstorm.features.weather.presentation.store.WeatherStore
import kotlinx.coroutines.flow.StateFlow

interface WeatherComponent {
    val model: StateFlow<WeatherStore.State>

    fun onEstimate(city: String, temperature: Int)

    fun onRetryEstimation()

    fun onCityValidate(city: String)

    fun onTemperatureValidate(temperature: String)

    fun onNewsClicked()

    fun onFavoritesClicked()
}