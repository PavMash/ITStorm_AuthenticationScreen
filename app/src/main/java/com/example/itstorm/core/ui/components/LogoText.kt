package com.example.itstorm.core.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.itstorm.R
import com.example.itstorm.core.ui.theme.GreyE5
import com.example.itstorm.core.ui.theme.robotoFlexFontFamily

@Composable
fun LogoText() {
    Text(
        text = stringResource(R.string.app_title),
        fontSize = 28.sp,
        fontFamily = robotoFlexFontFamily,
        fontWeight = FontWeight.W500,
        color = GreyE5
    )
}