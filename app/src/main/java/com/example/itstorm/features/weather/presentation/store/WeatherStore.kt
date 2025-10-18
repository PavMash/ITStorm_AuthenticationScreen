package com.example.itstorm.features.weather.presentation.store

import com.arkivanov.mvikotlin.core.store.Store
import com.example.itstorm.features.weather.domain.EstimationInput
import com.example.itstorm.features.weather.domain.EstimationState
import com.example.itstorm.features.weather.domain.ValidationResults
import com.example.itstorm.features.weather.domain.WeatherEstimation
import com.example.itstorm.features.weather.presentation.store.WeatherStore.Intent
import com.example.itstorm.features.weather.presentation.store.WeatherStore.State
import com.example.itstorm.features.weather.presentation.store.WeatherStore.Label

interface WeatherStore : Store<Intent, State, Label>{
    
    sealed interface Intent {
        data class EstimateCityWeather(val city : String, val temperature : Int) : Intent
        data object RetryEstimation : Intent
        data class ValidateCityName(val city: String): Intent
        data class ValidateTemperature(val temperature: String): Intent
        data object ClickNews: Intent
        data object ClickFavorites: Intent
    }

    data class State (
        val estimations : List<WeatherEstimation> = emptyList(),
        val estimationState: EstimationState =
            EstimationState.InProgress(
                EstimationInput(),
                ValidationResults()
            )
    )

    sealed interface Label {
        data object ClickNews: Label
        data object ClickFavorites: Label
    }
}