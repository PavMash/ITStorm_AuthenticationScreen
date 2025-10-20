package com.example.itstorm.features.authentication.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.itstorm.R
import com.example.itstorm.core.ui.theme.Black
import com.example.itstorm.core.ui.theme.Grey34
import com.example.itstorm.core.ui.theme.Red0C
import com.example.itstorm.features.authentication.domain.ValidationResult
import com.example.itstorm.core.ui.components.TextFieldLabel

@Composable
fun InputFields(
    modifier: Modifier = Modifier,
    login: String,
    password: String,
    loginErr: ValidationResult,
    passwordErr: ValidationResult,
    isPasswordVisible: Boolean,
    onPasswordVisibilityChange: (Boolean) -> Unit,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = login,
            label = { TextFieldLabel(
                stringResource(R.string.login_textfield_label)) },
            onValueChange = onLoginChange,
            isError = (loginErr != ValidationResult.Valid),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Black,
                unfocusedContainerColor = Black,
                errorContainerColor = Black,
                unfocusedIndicatorColor = Grey34,
                focusedIndicatorColor = Grey34,
                errorIndicatorColor = Red0C
            )
        )

        if (loginErr != ValidationResult.Valid) {
            ErrorMessage(validRes = loginErr)
        }

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            label = { TextFieldLabel(
                    stringResource(R.string.password_textfield_label)) },
            onValueChange = onPasswordChange,
            isError = (passwordErr != ValidationResult.Valid),
            visualTransformation = if (isPasswordVisible)
                VisualTransformation.None
            else PasswordVisualTransformation('\u002A'),
            trailingIcon = {
                PasswordVisibilityButton(
                    isPasswordVisible = isPasswordVisible,
                    onPasswordVisibilityChange = onPasswordVisibilityChange
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Black,
                unfocusedContainerColor = Black,
                errorContainerColor = Black,
                unfocusedIndicatorColor = Grey34,
                focusedIndicatorColor = Grey34,
                errorIndicatorColor = Red0C
            ),
        )

        if (passwordErr != ValidationResult.Valid) {
            ErrorMessage(validRes = passwordErr)
        }
    }
}