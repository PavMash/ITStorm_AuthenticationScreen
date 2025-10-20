package com.example.itstorm.features.authentication.presentation.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.itstorm.core.ui.theme.Red00
import com.example.itstorm.core.ui.theme.robotoFlexFontFamily
import com.example.itstorm.features.authentication.domain.ValidationResult
import com.example.itstorm.features.authentication.domain.toErrMessage

@Composable
fun ErrorMessage(
    modifier: Modifier = Modifier,
    validRes: ValidationResult
) {
    Text(
        modifier = modifier,
        text = validRes.toErrMessage(),
        fontFamily = robotoFlexFontFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.W500,
        color = Red00
    )
}