package com.example.itstorm.features.authentication.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.itstorm.R
import com.example.itstorm.core.ui.theme.Black
import com.example.itstorm.core.ui.theme.GreyC6
import com.example.itstorm.core.ui.theme.ITStorm_AuthenticationScreenTheme
import com.example.itstorm.core.ui.theme.robotoFlexFontFamily
import com.example.itstorm.features.authentication.domain.ValidationResult
import com.example.itstorm.features.authentication.presentation.components.InputFields
import com.example.itstorm.features.authentication.presentation.components.TextWithLogo
import com.example.itstorm.core.ui.components.MainButton

@Composable
fun AuthenticationUI(component: AuthenticationComponent) {
    ITStorm_AuthenticationScreenTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Black
        ) { innerPadding ->
            AuthenticationScreen(component, innerPadding)
        }
    }
}

@Composable
fun AuthenticationScreen(
    component: AuthenticationComponent,
    innerPadding: PaddingValues
) {
    val state by component.model.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(innerPadding)
    ) {

        TextWithLogo(
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .padding(top = 60.dp),
            text = stringResource(R.string.app_title)
        )

        Spacer(modifier = Modifier.height(178.dp))

        InputFields(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 12.dp),
            login = state.user.login,
            password = state.user.password,
            loginErr = state.loginErrMessage,
            passwordErr = state.passwordErrMessage,
            isPasswordVisible = state.isPasswordVisible,
            onPasswordVisibilityChange = component::onPasswordVisibilityChange,
            onLoginChange = component::onLoginValidate,
            onPasswordChange = component::onPasswordValidate
        )

        Spacer(modifier = Modifier.height(32.dp))

        MainButton(
            modifier = Modifier.align(Alignment.End).padding(end = 12.dp),
            enabled = (state.loginErrMessage == ValidationResult.Valid)
                    && (state.passwordErrMessage == ValidationResult.Valid),
            onClick = { component.onCredentialsSubmit(
                login = state.user.login,
                password = state.user.password) },
            label = stringResource(R.string.enter_button_text)
        )

        Spacer(modifier = Modifier.height(32.dp))

        TextButton(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 9.dp),
            content = { Text(
                    text = stringResource(R.string.skip_authorization_button_text),
                    fontFamily = robotoFlexFontFamily,
                    fontWeight = FontWeight.W500,
                    fontSize = 14.sp,
                    color = GreyC6) },
            onClick = component::onEnterWithoutAuth
        )
    }
}