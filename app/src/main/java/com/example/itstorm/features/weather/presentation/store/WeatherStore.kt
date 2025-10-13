package com.example.itstorm.features.weather.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.example.itstorm.features.weather.domain.WeatherEstimation
import com.example.itstorm.features.weather.presentation.store.WeatherStore.Intent
import com.example.itstorm.features.weather.presentation.store.WeatherStore.State

interface WeatherStore : Store<Intent, State, Nothing>{
    
    sealed interface Intent {
        data class EstimateCityWeather(val city : String, val temperature : Int) : Intent
    }

    data class State (
        val estimations : List<WeatherEstimation> = emptyList(),
        val latestEstimation : WeatherEstimation = WeatherEstimation(),
    )
}