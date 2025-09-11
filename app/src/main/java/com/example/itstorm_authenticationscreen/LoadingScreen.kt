package com.example.itstorm_authenticationscreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.itstorm_authenticationscreen.ui.theme.Grey
import com.example.itstorm_authenticationscreen.ui.theme.ITStorm_AuthenticationScreenTheme
import com.example.itstorm_authenticationscreen.ui.theme.robotoFlexFontFamily
import kotlinx.coroutines.delay

class LoadingScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ITStorm_AuthenticationScreenTheme {
                    SplashScreen {
                        val intent = Intent(this@LoadingScreen, AuthenticationScreen::class.java)
                        startActivity(intent)
                    }
            }
        }
    }
}

@Composable
private fun SplashScreen(onTimeout: () -> Unit) {
    LaunchedEffect(key1 = true, block = {
        delay(2000)
        onTimeout()
    })
    Surface {
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    painter = painterResource(R.drawable.circular_progress_bar),
                    contentDescription = stringResource(R.string.logo_image_description),
                    modifier = Modifier.size(width = 48.dp, height = 48.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Box(modifier = Modifier.size(width = 129.dp, height = 30.dp),
                    contentAlignment = Alignment.Center) {
                    Text(
                        text = stringResource(R.string.app_title),
                        fontSize = 28.sp,
                        fontFamily = robotoFlexFontFamily,
                        fontWeight = FontWeight(500)
                    )
                }
            }

            Text(
                text = stringResource(R.string.app_version),
                fontSize = 14.sp,
                fontFamily = robotoFlexFontFamily,
                fontWeight = FontWeight(500),
                color = Grey,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 64.dp)
            )
        }
    }
}
