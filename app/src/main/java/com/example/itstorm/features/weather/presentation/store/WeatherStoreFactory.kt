package com.example.itstorm.features.weather.presentation.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.itstorm.features.weather.domain.EstimationInput
import com.example.itstorm.features.weather.domain.EstimationState
import com.example.itstorm.features.weather.domain.ValidationResults
import com.example.itstorm.features.weather.domain.WeatherEstimation
import com.example.itstorm.features.weather.domain.copyWithSameTemperature
import com.example.itstorm.features.weather.domain.copyWithSameCity
import com.example.itstorm.features.weather.presentation.store.WeatherStore.Intent
import com.example.itstorm.features.weather.presentation.store.WeatherStore.State
import com.example.itstorm.features.weather.presentation.store.WeatherStore.Label
import kotlinx.coroutines.launch

class WeatherStoreFactory (private val storeFactory : StoreFactory) {

    fun create(): WeatherStore =
        object:  WeatherStore, Store<Intent, State, Label> by storeFactory.create(
            name = "WeatherStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::WeatherExecutor,
            reducer = WeatherReducer
        ) {}

    private sealed interface Msg {
        data class EstimationDone(val newEstimation: WeatherEstimation) : Msg
        data class CityNameValidated(val newCityName:String, val validationResult: Boolean): Msg
        data class TemperatureValidated(val newTemperature:String, val validationResult: Boolean): Msg
        data object BringBackEstimationInterface : Msg
    }

    private class WeatherExecutor: CoroutineExecutor<Intent, Unit, State, Msg, Label>() {
        override fun executeIntent(intent: Intent) =
            when(intent) {
                is Intent.EstimateCityWeather ->
                    estimateCityWeather(
                        city = intent.city,
                        temperature = intent.temperature
                    )
                is Intent.RetryEstimation ->
                    retryEstimation()
                is Intent.ValidateCityName ->
                    validateCityName(intent.city)
                is Intent.ValidateTemperature ->
                    validateTemperature(intent.temperature)
                is Intent.ClickNews ->
                    publish(Label.ClickNews)
                is Intent.ClickFavorites ->
                    publish(Label.ClickFavorites)
            }

        private fun estimateCityWeather(city: String, temperature: Int) {
            scope.launch {
                val verdict = when {
                    -30 <= temperature && temperature <= 13
                        -> "холодно"

                    14 <= temperature && temperature <= 25
                        -> "тепло"

                    26 <= temperature && temperature <= 35
                        -> "жарко"

                    else -> "катастрофа"
                }

                val newEstimation = WeatherEstimation(city, temperature, verdict)
                dispatch(Msg.EstimationDone(newEstimation))
            }
        }

        private fun validateCityName(city: String) {
            val nonEmptyAndBlank = city.isNotBlank() && city.isNotEmpty()
            val cyrillicPattern = Regex("\\p{IsCyrillic}")
            val containsCyrillicLetters = cyrillicPattern.containsMatchIn(city)

            val result = nonEmptyAndBlank && containsCyrillicLetters
            dispatch(Msg.CityNameValidated(city, result))
        }

        private fun validateTemperature(temperature: String) {
            val nonEmptyAndBlank = temperature.isNotBlank() && temperature.isNotEmpty()
            val numericPattern = Regex("^-?\\d+$")
            val isNumerical = numericPattern.matches(temperature)

            val result = nonEmptyAndBlank && isNumerical
            dispatch(Msg.TemperatureValidated(
                temperature,
                result))
        }

        private fun retryEstimation() {
            dispatch(Msg.BringBackEstimationInterface)
        }

    }

    private object WeatherReducer: Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.EstimationDone ->
                    copy(
                        estimations = listOf(msg.newEstimation) + estimations,
                        estimationState = EstimationState.Success(msg.newEstimation)
                    )

                is Msg.BringBackEstimationInterface ->
                    copy(
                        estimations = estimations,
                        estimationState = EstimationState.InProgress(
                            EstimationInput(),
                            ValidationResults()
                        )
                    )

                is Msg.CityNameValidated -> {
                    if (this.estimationState is EstimationState.InProgress) {
                        val newEstimationState =
                            this.estimationState.copyWithSameTemperature(
                                city = msg.newCityName,
                                isValid = msg.validationResult)

                        copy(estimations = estimations,
                            estimationState = newEstimationState)
                    } else {
                        this
                    }
                }

                is Msg.TemperatureValidated -> {
                    if (this.estimationState is EstimationState.InProgress) {
                        val newEstimationState =
                            this.estimationState.copyWithSameCity(
                                temperature = msg.newTemperature,
                                isValid = msg.validationResult)

                        copy(estimations = estimations,
                            estimationState = newEstimationState)
                    } else {
                        this
                    }
                }

            }
    }
}