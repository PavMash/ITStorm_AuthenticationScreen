package com.example.itstorm.features.weather.presentation.components

import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.itstorm.core.ui.theme.Black
import com.example.itstorm.core.ui.theme.Grey34
import com.example.itstorm.core.ui.theme.White

@Composable
fun EstimationTextField(
    value: String,
    validate: (String) -> Unit,
    label: String,
    modifier: Modifier
) {
    TextField(
        modifier = modifier,
        value = value.trim(),
        onValueChange = validate,
        label = {
            TextFieldLabel(
                label
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