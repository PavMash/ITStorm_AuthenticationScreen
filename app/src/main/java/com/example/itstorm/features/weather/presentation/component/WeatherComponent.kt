package com.example.itstorm.features.weather.presentation.component

import kotlinx.coroutines.flow.StateFlow
import com.example.itstorm.features.weather.presentation.store.WeatherStore

interface WeatherComponent {
    val model: StateFlow<WeatherStore.State>

    fun onEstimate(city: String, temperature: Int)

    fun onRetryEstimation()
}