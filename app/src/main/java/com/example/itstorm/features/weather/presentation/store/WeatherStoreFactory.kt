package com.example.itstorm.features.weather.presentation.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.example.itstorm.features.weather.domain.WeatherEstimation
import com.example.itstorm.features.weather.presentation.store.WeatherStore.Intent
import com.example.itstorm.features.weather.presentation.store.WeatherStore.State
import kotlinx.coroutines.launch

class WeatherStoreFactory (private val storeFactory : StoreFactory) {

    fun create(): WeatherStore =
        object:  WeatherStore, Store<Intent, State, Nothing> by storeFactory.create(
            name = "WeatherStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::WeatherExecutor,
            reducer = WeatherReducer
        ) {}

    private sealed interface Msg {
        data class EstimationDone(val newEstimation: WeatherEstimation) : Msg
        object BringBackEstimationInterface : Msg
    }

    private class WeatherExecutor: CoroutineExecutor<Intent, Unit, State, Msg, Nothing>() {
        override fun executeIntent(intent: Intent) =
            when(intent) {
                is Intent.EstimateCityWeather -> estimateCityWeather(
                    city = intent.city,
                    temperature = intent.temperature
                )
                is Intent.RetryEstimation -> retryEstimation()
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

        private fun retryEstimation() {
            dispatch(Msg.BringBackEstimationInterface)
        }

    }

    private object WeatherReducer: Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when(msg) {
                is Msg.EstimationDone
                     -> copy(estimations = listOf(msg.newEstimation) + estimations,
                         latestEstimation = msg.newEstimation,
                         estimationInProgress = !estimationInProgress)
                is Msg.BringBackEstimationInterface
                    -> copy(estimations = estimations,
                        latestEstimation = latestEstimation,
                        estimationInProgress = !estimationInProgress)
            }
    }
}