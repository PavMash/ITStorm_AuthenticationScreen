package com.example.itstorm_authenticationscreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.itstorm_authenticationscreen.ui.theme.Black
import com.example.itstorm_authenticationscreen.ui.theme.ITStorm_AuthenticationScreenTheme
import com.example.itstorm_authenticationscreen.ui.theme.robotoFlexFontFamily

class AuthenticationScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ITStorm_AuthenticationScreenTheme {
                Surface {
                    Authentication()
                }
            }
        }
    }
}

@Composable
private fun Authentication() {
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
                fontWeight = FontWeight(500)
            )

            Spacer(modifier = Modifier.width(2.dp))

            Image(
                painter = painterResource(R.drawable.circular_progress_bar),
                contentDescription = stringResource(R.string.logo_image_description),
                modifier = Modifier.size(width = 48.dp, height = 48.dp)
            )
        }

        Spacer(modifier = Modifier.height(178.dp))
    }
}