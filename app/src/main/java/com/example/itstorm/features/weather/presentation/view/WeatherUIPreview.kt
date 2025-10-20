package com.example.itstorm.features.weather.presentation.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.itstorm.core.ui.theme.ITStorm_AuthenticationScreenTheme
import com.example.itstorm.features.weather.domain.EstimationState
import com.example.itstorm.features.weather.domain.WeatherEstimation
import com.example.itstorm.features.weather.presentation.store.WeatherStore
import kotlinx.coroutines.flow.MutableStateFlow

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun WeatherUIPreview() {
    ITStorm_AuthenticationScreenTheme {
        val fakeComponent = object : WeatherComponent {
            override val model = MutableStateFlow(
                WeatherStore.State(
                    estimationState = EstimationState.Success(
                        estimation = WeatherEstimation(
                            city = "Ульяновск",
                            temperature = 25,
                            verdict = "тепло"
                        )
                    ),
                    estimations = listOf(
                        WeatherEstimation("Москва", 30, "жарко"),
                        WeatherEstimation("Париж", 18, "тепло"),
                        WeatherEstimation("Томск", 20, "тепло")
                    )
                )
            )

            override fun onEstimate(city: String, temperature: Int) {}
            override fun onRetryEstimation() {}
            override fun onNewsClicked() {}
            override fun onFavoritesClicked() {}
            override fun onCityValidate(city: String) {}
            override fun onTemperatureValidate(temperature: String) {}
        }

        WeatherUI(component = fakeComponent)
    }
}
