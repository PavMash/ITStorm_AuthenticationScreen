package com.example.itstorm.core.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.itstorm.R
import com.example.itstorm.core.ui.theme.GreyE5
import com.example.itstorm.core.ui.theme.robotoFlexFontFamily

@Composable
fun SectionTitle(modifier: Modifier) {
    Text(
        text = stringResource(R.string.weather_page_title),
        fontFamily = robotoFlexFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 18.sp,
        color = GreyE5,
        modifier = modifier
    )
}