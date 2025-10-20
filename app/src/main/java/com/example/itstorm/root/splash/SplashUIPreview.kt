package com.example.itstorm.root.splash

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.itstorm.core.ui.theme.ITStorm_AuthenticationScreenTheme
import kotlinx.coroutines.flow.MutableStateFlow

@Preview(
    showSystemUi = true,
    showBackground = true,
    name = "Splash Screen Preview"
)
@Composable
fun SplashUIPreview() {
    ITStorm_AuthenticationScreenTheme {
        val fakeComponent = object : SplashComponent {
            override val model = MutableStateFlow(
                SplashComponent.SplashState(isLoading = true)
            )
        }

        SplashUI(component = fakeComponent)
    }
}
