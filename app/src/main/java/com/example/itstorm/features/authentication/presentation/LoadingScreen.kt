package com.example.itstorm.features.authentication.presentation
//
//import android.content.Intent
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.SystemBarStyle
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.animation.core.LinearEasing
//import androidx.compose.animation.core.animateFloat
//import androidx.compose.animation.core.infiniteRepeatable
//import androidx.compose.animation.core.rememberInfiniteTransition
//import androidx.compose.animation.core.tween
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.graphicsLayer
//import androidx.compose.ui.graphics.toArgb
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.itstorm.R
//import com.example.itstorm.core.ui.theme.Black
//import com.example.itstorm.core.ui.theme.Grey67
//import com.example.itstorm.core.ui.theme.GreyE5
//import com.example.itstorm.core.ui.theme.ITStorm_AuthenticationScreenTheme
//import com.example.itstorm.core.ui.theme.robotoFlexFontFamily
//import kotlinx.coroutines.delay
//
//class LoadingScreen : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            ITStorm_AuthenticationScreenTheme {
//                    SplashScreen {
//                        val intent = Intent(this@LoadingScreen, AuthenticationScreen::class.java)
//                        startActivity(intent)
//                    }
//            }
//        }
//    }
//}
//
//@Composable
//private fun SplashScreen(onTimeout: () -> Unit) {
//    LaunchedEffect(key1 = true, block = {
//        delay(2000)
//        onTimeout()
//    })
//
//    val context = LocalContext.current as ComponentActivity
//    context.enableEdgeToEdge(
//        statusBarStyle = SystemBarStyle.dark(Color.Black.toArgb()),
//        navigationBarStyle = SystemBarStyle.dark(Color.Black.toArgb())
//    )
//
//    Surface(color = Black) {
//        Box(modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center) {
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//
//                val infiniteTransition = rememberInfiniteTransition()
//                val rotate by infiniteTransition.animateFloat(
//                    initialValue = 0f,
//                    targetValue = 360f,
//                    animationSpec = infiniteRepeatable(
//                        animation = tween(durationMillis = 1500, easing = LinearEasing)
//                    )
//                )
//
//                Image(
//                    painter = painterResource(R.drawable.logo),
//                    contentDescription = stringResource(R.string.logo_image_description),
//                    modifier = Modifier.size(width = 48.dp, height = 48.dp)
//                        .graphicsLayer { rotationZ = rotate }
//                )
//
//                Spacer(modifier = Modifier.height(12.dp))
//
//                Box(modifier = Modifier.size(width = 129.dp, height = 30.dp),
//                    contentAlignment = Alignment.Center) {
//                    Text(
//                        text = stringResource(R.string.app_title),
//                        fontSize = 28.sp,
//                        fontFamily = robotoFlexFontFamily,
//                        fontWeight = FontWeight(500),
//                        color = GreyE5
//                    )
//                }
//            }
//
//            Text(
//                text = stringResource(R.string.app_version),
//                fontSize = 14.sp,
//                fontFamily = robotoFlexFontFamily,
//                fontWeight = FontWeight(500),
//                color = Grey67,
//                modifier = Modifier
//                    .align(Alignment.BottomCenter)
//                    .padding(bottom = 64.dp)
//            )
//        }
//    }
//}
