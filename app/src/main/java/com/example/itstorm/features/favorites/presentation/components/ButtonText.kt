package com.example.itstorm.features.favorites.presentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.itstorm.core.ui.theme.robotoFlexFontFamily

@Composable
fun ButtonText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color
) {
    Text(
        text = text,
        fontFamily = robotoFlexFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        color = color
    )
}