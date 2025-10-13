package com.example.itstorm.features.weather.presentation.view

import android.graphics.Paint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.example.itstorm.R
import com.example.itstorm.features.weather.presentation.component.WeatherComponent
import com.example.itstorm.features.weather.presentation.component.WeatherComponentImpl
import com.example.itstorm.features.weather.presentation.store.WeatherStoreFactory
import com.example.itstorm.features.weather.presentation.view.ui.theme.Black
import com.example.itstorm.features.weather.presentation.view.ui.theme.Grey1A
import com.example.itstorm.features.weather.presentation.view.ui.theme.Grey34
import com.example.itstorm.features.weather.presentation.view.ui.theme.Grey67
import com.example.itstorm.features.weather.presentation.view.ui.theme.GreyE5
import com.example.itstorm.features.weather.presentation.view.ui.theme.ITStorm_AuthenticationScreenTheme
import com.example.itstorm.features.weather.presentation.view.ui.theme.White
import com.example.itstorm.features.weather.presentation.view.ui.theme.robotoFlexFontFamily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Text

class WeatherScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val component = WeatherComponentImpl(storeFactory = WeatherStoreFactory(
            storeFactory = DefaultStoreFactory()),
            componentContext = defaultComponentContext())

        enableEdgeToEdge()
        setContent {
            ITStorm_AuthenticationScreenTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Black,
                ) { innerPadding ->
                    WeatherScreen(component, innerPadding)
                }
            }
        }
    }
}

@Composable
private fun WeatherScreen(component: WeatherComponent, innerPadding: PaddingValues) {
    val state by component.model.collectAsState()

    Column(modifier = Modifier.fillMaxSize()
        .padding(innerPadding)) {
        Text(
            text = stringResource(R.string.weather_page_title),
            fontFamily = robotoFlexFontFamily,
            fontWeight = FontWeight(500),
            fontSize = 18.sp,
            color = GreyE5,
            modifier = Modifier.align(Alignment.Start)
                .padding(start = 17.dp, top = 38.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        if (state.estimationInProgress) CityAndTemperatureInput(component)
        else LatestEstimationCard(state.latestEstimation.city,
            state.latestEstimation.temperature,
            state.latestEstimation.verdict)
    }
}

@Composable
private fun CityAndTemperatureInput(component: WeatherComponent) {
    val city = remember {
        mutableStateOf(value = "")
    }
    val temperature = remember {
        mutableStateOf(value = "")
    }
    val isCityValid = remember {
        mutableStateOf(value = false)
    }
    val isTemperatureValid = remember {
        mutableStateOf(value = false)
    }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxWidth()
        .padding(start = 7.dp, end = 7.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            value = city.value,
            onValueChange = { newVal ->
                city.value = newVal.trim()
                scope.launch {
                    isCityValid.value = validateCityInput(city.value)
                }
            },
            label = {
                TextFieldLabel(
                stringResource(R.string.city_textfield_label)
            )},
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Black,
                focusedContainerColor = Black,
                unfocusedIndicatorColor = Grey34,
                focusedIndicatorColor = Grey34,
                unfocusedTextColor = White,
                focusedTextColor = White,
            )
        )

        TextField(
            value = temperature.value,
            onValueChange = { newVal ->
                temperature.value = newVal.trim()
                scope.launch {
                    isTemperatureValid.value =
                        validateTemperatureInput(temperature.value)
                }
            },
            label = {
                TextFieldLabel(
                    stringResource(R.string.temperature_textfield_label)
                )},
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Black,
                focusedContainerColor = Black,
                unfocusedIndicatorColor = Grey34,
                focusedIndicatorColor = Grey34,
                unfocusedTextColor = White,
                focusedTextColor = White,
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            modifier = Modifier.align(Alignment.End).padding(end = 12.dp),
            onClick = {
                component.onEstimate(
                    city.value,
                    temperature.value.toInt())
            },
            content = {
                Text(
                    text = stringResource(R.string.estimate_button_text),
                    fontFamily = robotoFlexFontFamily,
                    fontWeight = FontWeight(500),
                    fontSize = 14.sp
                    )
            },
            shape = RoundedCornerShape(4.dp),
            enabled = isCityValid.value && isTemperatureValid.value,
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = Grey67,
                containerColor = GreyE5,
                disabledContentColor = Grey34,
                contentColor = Grey1A
            )
        )
    }
}

@Composable
private fun TextFieldLabel(label: String) {
    Text(
        text = label,
        fontFamily = robotoFlexFontFamily,
        fontWeight = FontWeight(400),
        fontSize = 16.sp,
        color = Grey67
    )
}

private suspend fun validateCityInput(city: String): Boolean {
    return withContext(Dispatchers.IO) {
        val nonEmptyAndBlank = city.isNotBlank()
                &&city.isNotEmpty()

        val cyrillicPattern = Regex("\\p{IsCyrillic}")
        val containsCyrillicLetters = cyrillicPattern.containsMatchIn(city)

        nonEmptyAndBlank && containsCyrillicLetters
    }
}

private suspend fun validateTemperatureInput(temperature: String): Boolean {
    return withContext(Dispatchers.IO) {
        val nonEmptyAndBlank = temperature.isNotBlank()
                &&temperature.isNotEmpty()

        val numericPattern = Regex("^-?\\d+$")
        val isNumerical = numericPattern.matches(temperature)

        nonEmptyAndBlank && isNumerical
    }
}

@Composable
private fun LatestEstimationCard(city: String, temperature: Int, verdict: String) {
    Text("City: $city, Temperature: $temperature, Verdict: $verdict",
        color = White,
        fontSize = 20.sp)
}
