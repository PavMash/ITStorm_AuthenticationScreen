package com.example.itstorm.features.weather.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.itstorm.R
import com.example.itstorm.core.ui.theme.Grey1A
import com.example.itstorm.core.ui.theme.Grey34
import com.example.itstorm.core.ui.theme.GreyE5
import com.example.itstorm.core.ui.theme.White
import com.example.itstorm.core.ui.theme.robotoFlexFontFamily

@Composable
fun EstimationCardContent(
    city: String,
    temperature: Int,
    innerPadding: PaddingValues) {
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
                fontWeight = FontWeight.W400,
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
                fontWeight = FontWeight.W400,
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