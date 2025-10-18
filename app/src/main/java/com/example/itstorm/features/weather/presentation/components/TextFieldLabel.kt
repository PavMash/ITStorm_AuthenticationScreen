package com.example.itstorm.features.weather.presentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.itstorm.core.ui.theme.Grey67
import com.example.itstorm.core.ui.theme.robotoFlexFontFamily

@Composable
fun TextFieldLabel(label: String) {
    Text(
        text = label,
        fontFamily = robotoFlexFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        color = Grey67
    )
}