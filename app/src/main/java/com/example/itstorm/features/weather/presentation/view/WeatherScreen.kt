package com.example.itstorm.features.weather.presentation.view

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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
import com.example.itstorm.features.weather.presentation.view.ui.theme.Grey34
import com.example.itstorm.features.weather.presentation.view.ui.theme.Grey67
import com.example.itstorm.features.weather.presentation.view.ui.theme.GreyE5
import com.example.itstorm.features.weather.presentation.view.ui.theme.ITStorm_AuthenticationScreenTheme
import com.example.itstorm.features.weather.presentation.view.ui.theme.White
import com.example.itstorm.features.weather.presentation.view.ui.theme.robotoFlexFontFamily

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
                    WeatherInterface(component, innerPadding)
                }
            }
        }
    }
}

@Composable
private fun WeatherInterface(component: WeatherComponent, innerPadding: PaddingValues) {
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

        val city = remember {
            mutableStateOf(value = "")
        }
        val temperature = remember {
            mutableStateOf(value = "")
        }
        CityAndTemperatureInput(city, temperature)
    }
}

@Composable
private fun CityAndTemperatureInput(city: MutableState<String>,
                                    temperature: MutableState<String>) {
    Column(modifier = Modifier.fillMaxWidth()
        .padding(start = 7.dp, end = 7.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            value = city.value,
            onValueChange = { newVal ->
               city.value = newVal
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
                temperature.value = newVal
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