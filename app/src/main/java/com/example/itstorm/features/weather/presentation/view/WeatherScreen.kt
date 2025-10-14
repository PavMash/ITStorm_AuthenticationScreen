package com.example.itstorm.features.weather.presentation.view

import android.graphics.Paint
import android.os.Bundle
import android.text.Layout
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.example.itstorm.R
import com.example.itstorm.features.weather.presentation.component.WeatherComponent
import com.example.itstorm.features.weather.presentation.component.WeatherComponentImpl
import com.example.itstorm.features.weather.presentation.store.WeatherStoreFactory
import com.example.itstorm.features.weather.domain.WeatherEstimation
import com.example.itstorm.features.weather.presentation.view.ui.theme.Black
import com.example.itstorm.features.weather.presentation.view.ui.theme.Grey1A
import com.example.itstorm.features.weather.presentation.view.ui.theme.Grey2F
import com.example.itstorm.features.weather.presentation.view.ui.theme.Grey34
import com.example.itstorm.features.weather.presentation.view.ui.theme.Grey67
import com.example.itstorm.features.weather.presentation.view.ui.theme.GreyB2
import com.example.itstorm.features.weather.presentation.view.ui.theme.GreyDD
import com.example.itstorm.features.weather.presentation.view.ui.theme.GreyE4tr20
import com.example.itstorm.features.weather.presentation.view.ui.theme.GreyE5
import com.example.itstorm.features.weather.presentation.view.ui.theme.ITStorm_AuthenticationScreenTheme
import com.example.itstorm.features.weather.presentation.view.ui.theme.White
import com.example.itstorm.features.weather.presentation.view.ui.theme.robotoFlexFontFamily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val component = WeatherComponentImpl(storeFactory = WeatherStoreFactory(
            storeFactory = DefaultStoreFactory()),
            componentContext = defaultComponentContext())

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(Color.Black.toArgb()),
            navigationBarStyle = SystemBarStyle.dark(Color.Black.toArgb())
        )
        setContent {
            ITStorm_AuthenticationScreenTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Black,
                    bottomBar = {
                        BottomAppBar(
                            modifier = Modifier.fillMaxWidth()
                                .windowInsetsPadding(
                                WindowInsets.navigationBars),
                            containerColor = Black
                        ) {
                            BottomNavBar()
                        }
                    }
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
        else LatestEstimationCard(component,
            state.latestEstimation.city,
            state.latestEstimation.temperature,
            state.latestEstimation.verdict)

        Spacer(modifier = Modifier.height(40.dp))

        EstimationHistorySection(component = component)
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
        .padding(horizontal = 7.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
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
            modifier = Modifier.fillMaxWidth(),
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
            modifier = Modifier.align(Alignment.End),
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
private fun LatestEstimationCard(component: WeatherComponent, city: String, temperature: Int, verdict: String) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(start = 7.dp, end = 7.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = Grey1A,
                disabledContainerColor = Grey1A
            )
        ) {
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                EstimationCardContent(city, temperature,
                    PaddingValues(bottom = 10.dp))

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Grey34
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = stringResource(R.string.verdict_template) + "$city $verdict",
                    fontFamily = robotoFlexFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500),
                    color = White
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            modifier = Modifier.align(Alignment.End)
                .padding(start = 12.dp, end = 12.dp,
                    top = 4.dp, bottom = 4.dp),
            onClick = { component.onRetryEstimation() },
            content = {
                Text(
                    text = stringResource(R.string.retry_estimation_button_text),
                    fontFamily = robotoFlexFontFamily,
                    fontWeight = FontWeight(500),
                    fontSize = 14.sp
                )
            },
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = GreyE5,
                contentColor = Grey1A
            )
        )
    }
}

@Composable
private fun EstimationCardContent(city: String, temperature: Int, innerPadding: PaddingValues) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(innerPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .wrapContentSize()
                .background(
                    color = Grey1A,
                    shape = RoundedCornerShape(4.dp))
                .border(
                    color = Grey34,
                    width = 1.dp,
                    shape = RoundedCornerShape(4.dp))
                .padding(vertical = 6.dp,
                    horizontal = 10.dp)
        ) {
            Text(
                text = city.uppercase(),
                fontFamily = robotoFlexFontFamily,
                fontSize = 14.sp,
                fontWeight = FontWeight(400),
                color = GreyE5
            )
        }

        Row(
            modifier = Modifier.wrapContentSize()
                .padding(6.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.union),
                contentDescription = stringResource(R.string.temperature_icon_description_text),
                tint = White
            )

            Spacer(modifier = Modifier.width(7.25.dp))

            Text(
                text = createTemperatureString(temperature),
                fontFamily = robotoFlexFontFamily,
                fontWeight = FontWeight(400),
                fontSize = 14.sp,
                color = GreyE5
            )
        }
    }
}

private fun createTemperatureString(temperature: Int): String {
    val degreeSign = 0x00B0
    var tempStr = if (temperature > 0) "+" else ""
    tempStr += temperature.toString() + degreeSign.toChar().toString() + "C"
    return tempStr
}

@Composable
private fun EstimationHistorySection(component: WeatherComponent) {
    val state by component.model.collectAsState()
    val history = state.estimations

    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 7.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.align(Alignment.Start)
                .padding(start = 10.dp),
            text = stringResource(R.string.search_history_section_title),
            fontFamily = robotoFlexFontFamily,
            fontWeight = FontWeight(500),
            fontSize = 16.sp,
            color = GreyE5
        )

        Spacer(modifier = Modifier.height(6.dp))

        if (history.isNotEmpty())
            EstimationHistory(state.estimations)
        else EstimationHistoryPlaceholder()
    }
}

@Composable
private fun EstimationHistory(estimations: List<WeatherEstimation>) {
    LazyColumn (
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(estimations) { estimation ->
            EstimationHistoryCard(estimation)
        }
    }
}

@Composable
private fun EstimationHistoryCard(estimation: WeatherEstimation) {
    Card(
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Grey1A,
            disabledContainerColor = Grey1A
        ),
        content = {
            EstimationCardContent(
            estimation.city,
            estimation.temperature,
                PaddingValues(12.dp))
        }
    )
}

@Composable
private fun EstimationHistoryPlaceholder() {
    Box(
        modifier = Modifier.fillMaxWidth()
            .background(
                color = Grey2F,
                shape = RoundedCornerShape(6.dp))
            .padding(top = 8.dp, bottom = 8.dp,
                start = 11.5.dp, end = 11.5.dp),
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(R.string.search_history_placeholder_text),
            fontFamily = robotoFlexFontFamily,
            fontWeight = FontWeight(500),
            fontSize = 14.sp,
            color = GreyB2
        )
    }
}

@Composable
private fun BottomNavBar() {
    Row(modifier = Modifier.fillMaxWidth()
        .height(IntrinsicSize.Min)
        .padding(horizontal = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(
            6.dp,
            Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavButton(
            painterResource(R.drawable.weather),
            label = stringResource(R.string.weather_page_navigation_text),
            enabled = false, Modifier.weight(1f).fillMaxHeight()
        ) { }

        BottomNavButton(
            painterResource(R.drawable.news),
            label = stringResource(R.string.news_page_navigation_text),
            enabled = true, Modifier.weight(1f).fillMaxHeight()
        ) { }

        BottomNavButton(
            painterResource(R.drawable.heart),
            label = stringResource(R.string.favourites_page_navigation_text),
            enabled = true, Modifier.weight(1f).fillMaxHeight(),
        ) { }
    }
}

@Composable
private fun BottomNavButton(painter: Painter, label: String, enabled: Boolean,
                            modifiers: Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(3.6.dp),
        border = BorderStroke(
            width = 0.6.dp,
            color = GreyDD),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Black,
            contentColor = White,
            disabledContainerColor = GreyE4tr20,
            disabledContentColor = White
        ),
        modifier = modifiers
    ) {
        Column(
            //modifier = Modifier.padding(vertical = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ){
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painter,
                contentDescription = label
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = label,
                fontFamily = robotoFlexFontFamily,
                fontWeight = FontWeight(400),
                fontSize = 10.8.sp,
            )
        }
    }
}