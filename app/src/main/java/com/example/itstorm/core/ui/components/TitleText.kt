package com.example.itstorm.core.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.itstorm.core.ui.theme.White
import com.example.itstorm.core.ui.theme.robotoFlexFontFamily

@Composable
fun TitleText(
    modifier: Modifier = Modifier,
    color: Color = White,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = 28.sp,
        fontFamily = robotoFlexFontFamily,
        fontWeight = FontWeight.W500,
        color = color
    )
}