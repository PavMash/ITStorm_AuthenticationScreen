package com.example.itstorm.features.authentication.presentation.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.itstorm.core.ui.theme.ITStorm_AuthenticationScreenTheme
import com.example.itstorm.features.authentication.domain.User
import com.example.itstorm.features.authentication.domain.ValidationResult
import com.example.itstorm.features.authentication.presentation.store.AuthenticationStore
import kotlinx.coroutines.flow.MutableStateFlow

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Authentication Screen Preview"
)
@Composable
fun AuthenticationUIPreview() {
    ITStorm_AuthenticationScreenTheme {
        val fakeComponent = object : AuthenticationComponent {
            override val model = MutableStateFlow(
                AuthenticationStore.AuthState(
                    user = User(
                        login = "Логин_Юзера",
                        password = "a123456"
                    ),
                    loginErrMessage = ValidationResult.Valid,
                    passwordErrMessage = ValidationResult.Valid,
                    isPasswordVisible = false
                )
            )

            override fun onLoginValidate(login: String) {}
            override fun onPasswordValidate(password: String) {}
            override fun onCredentialsSubmit(login: String, password: String) {}
            override fun onPasswordVisibilityChange(isVisible: Boolean) {}
            override fun onEnterWithoutAuth() {}
        }

        AuthenticationUI(component = fakeComponent)
    }
}
