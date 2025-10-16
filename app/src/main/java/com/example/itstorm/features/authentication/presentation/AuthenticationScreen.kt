package com.example.itstorm.features.authentication.presentation

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.itstorm.R
import com.example.itstorm.features.weather.presentation.view.WeatherScreen
import com.example.itstorm.features.weather.presentation.view.ui.theme.Black
import com.example.itstorm.features.weather.presentation.view.ui.theme.Grey1A
import com.example.itstorm.features.weather.presentation.view.ui.theme.Grey34
import com.example.itstorm.features.weather.presentation.view.ui.theme.Grey67
import com.example.itstorm.features.weather.presentation.view.ui.theme.GreyC6
import com.example.itstorm.features.weather.presentation.view.ui.theme.GreyE5
import com.example.itstorm.features.weather.presentation.view.ui.theme.ITStorm_AuthenticationScreenTheme
import com.example.itstorm.features.weather.presentation.view.ui.theme.Red00
import com.example.itstorm.features.weather.presentation.view.ui.theme.Red0C
import com.example.itstorm.features.weather.presentation.view.ui.theme.White
import com.example.itstorm.features.weather.presentation.view.ui.theme.robotoFlexFontFamily

class AuthenticationScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.Black.toArgb()),
            navigationBarStyle = SystemBarStyle.dark(Color.Black.toArgb()))
        setContent {
            ITStorm_AuthenticationScreenTheme {
                Surface(color = Black) {
                    Authentication()
                }
            }
        }
    }
}

@Composable
private fun Authentication() {
    val context = LocalContext.current as ComponentActivity

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .padding(top = 60.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.app_title),
                fontSize = 28.sp,
                fontFamily = robotoFlexFontFamily,
                fontWeight = FontWeight(500),
                color = GreyE5
            )

            Spacer(modifier = Modifier.width(2.dp))

            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = stringResource(R.string.logo_image_description),
                modifier = Modifier.size(width = 48.dp, height = 48.dp)
            )
        }

        Spacer(modifier = Modifier.height(178.dp))

        var login by remember {
            mutableStateOf(value = "")
        }
        var loginError by remember {
            mutableStateOf(value = "")
        }
        TextField(
            value = login,
            label = {
                Text(
                text = stringResource(R.string.login_textfield_label),
                fontFamily = robotoFlexFontFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight(500),
                color = Grey67,
            )},
            onValueChange = {newText : String ->
                login = newText
                loginError = if (login.isNotBlank()) validateLogin(login) else ""
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Black,
                unfocusedContainerColor = Black,
                errorContainerColor = Black,
                unfocusedIndicatorColor = Grey34,
                focusedIndicatorColor = Grey34,
                errorIndicatorColor = Red0C
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp)
                .height(56.dp),
            isError = loginError.isNotEmpty(),
        )

        if (loginError.isNotEmpty()) {
            ErrorMessage(loginError)
        }

        var passwordVisible by remember {
            mutableStateOf(value = false)
        }
        var password by remember {
            mutableStateOf(value = "")
        }
        var passwordError by remember {
            mutableStateOf(value = "")
        }
        TextField(
            value = password,
            label = {
                Text(
                    text = stringResource(R.string.password_textfield_label),
                    fontFamily = robotoFlexFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(400),
                    color = Grey67,
                )},
            onValueChange = {newText : String ->
                password = newText
                passwordError = if (password.isNotBlank()) validatePassword(password) else ""
            },
            isError = passwordError.isNotEmpty(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Black,
                unfocusedContainerColor = Black,
                errorContainerColor = Black,
                unfocusedIndicatorColor = Grey34,
                focusedIndicatorColor = Grey34,
                errorIndicatorColor = Red0C
            ),
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp)
                .height(56.dp),
            visualTransformation = if (passwordVisible) VisualTransformation.None
                    else PasswordVisualTransformation('\u002A'),
            trailingIcon = {
                IconToggleButton(
                    checked = passwordVisible,
                    onCheckedChange = {
                        passwordVisible = !passwordVisible
                    },
                ) {
                    Icon(
                        modifier = Modifier.size(width = 48.dp, height = 48.dp),
                        painter = painterResource(if (!passwordVisible) R.drawable.show_password_icon
                        else R.drawable.hide_password_icon),
                        contentDescription = stringResource(R.string.password_visibility_description)
                    )
                }
            },
        )

        if (passwordError.isNotEmpty()) {
            ErrorMessage(passwordError)
        }

        Spacer(modifier = Modifier.height(32.dp))

        val interactionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsPressedAsState()

        Button(
          modifier = Modifier.align(Alignment.End).padding(end = 12.dp),
            interactionSource = interactionSource,
            onClick = {},
            content = {
                Text(
                    text = stringResource(R.string.enter_button_text),
                    fontFamily = robotoFlexFontFamily,
                    fontWeight = FontWeight(500),
                    fontSize = 14.sp,
                )},
            shape = RoundedCornerShape(4.dp),
            enabled = login.isNotBlank()
                    &&password.isNotBlank()
                    &&loginError.isEmpty()
                    &&passwordError.isEmpty(),
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = Grey67,
                containerColor = if (isPressed) White else GreyE5,
                disabledContentColor = Grey34,
                contentColor = if (isPressed) Black else Grey1A
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        TextButton(
            content = {
                Text(
                text = stringResource(R.string.skip_authorization_button_text),
                fontFamily = robotoFlexFontFamily,
                fontWeight = FontWeight(500),
                fontSize = 14.sp,
                color = GreyC6
            )},
            onClick = {
                val intent = Intent(context, WeatherScreen::class.java)
                context.startActivity(intent)
            },
            modifier = Modifier.fillMaxWidth().padding(start = 9.dp, end = 9.dp)
        )
    }
}

@Composable
private fun ErrorMessage(errCode: String) {
    val errorMessage = when (errCode) {
        "not_cyrillic" -> stringResource(R.string.not_cyrillic_letter)
        "wrong_login" -> stringResource(R.string.wrong_login)
        "none_latin" -> stringResource(R.string.no_latin_letters_in_password)
        "none_digit" -> stringResource(R.string.no_digits_in_password)
        "short_password" -> stringResource(R.string.short_password)
        else -> ""
    }

    return Text(
        text = errorMessage,
        fontFamily = robotoFlexFontFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight(500),
        color = Red00,
        modifier = Modifier.padding(start = 23.dp)
    )
}

private fun validateLogin(login: String) : String {
    val approvedLogin = "Логин_Юзера"
    val cyrillicRegex = Regex("^[А-Яа-яЁё_]+$")

    if (!cyrillicRegex.matches(login)) {
        return "not_cyrillic"
    }

    if (login != approvedLogin) {
        return "wrong_login"
    }

    return ""
}

private fun validatePassword(password: String) : String {
    val latinRegex = Regex("^(?=.*[A-Za-z]).+$")
    val digitRegex = Regex("^(?=.*\\d).+$")

    if (!latinRegex.matches(password)) {
        return "none_latin"
    }

    if (!digitRegex.matches(password)) {
        return "none_digit"
    }

    if (password.length < 6) {
        return "short_password"
    }

    return ""
}

@Preview
@Composable
fun TextBut() {
    TextButton(
        content = {
            Text(
                text = stringResource(R.string.skip_authorization_button_text),
                fontFamily = robotoFlexFontFamily,
                fontWeight = FontWeight(500),
                fontSize = 14.sp,
                color = GreyC6
            )},
        onClick = { },
        modifier = Modifier.fillMaxWidth().padding(start = 9.dp, end = 9.dp)
    )
}