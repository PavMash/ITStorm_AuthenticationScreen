package com.example.itstorm.core.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.itstorm.core.ui.theme.Black
import com.example.itstorm.core.ui.theme.GreyDD
import com.example.itstorm.core.ui.theme.GreyE4tr20
import com.example.itstorm.core.ui.theme.White
import com.example.itstorm.core.ui.theme.robotoFlexFontFamily

@Composable
fun BottomNavButton(painter: Painter,
                    label: String,
                    enabled: Boolean,
                    modifier: Modifier,
                    onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(3.6.dp),
        border = BorderStroke(
            width = 0.6.dp,
            color = GreyDD),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = Black,
            contentColor = White,
            disabledContainerColor = GreyE4tr20,
            disabledContentColor = White
        ),
        modifier = modifier
    ) {
        Column(
            //modifier = Modifier.padding(vertical = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ){
            Icon(
                modifier = Modifier.size(16.dp),
                painter = painter,
                contentDescription = label
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = label,
                fontFamily = robotoFlexFontFamily,
                fontWeight = FontWeight(400),
                fontSize = 10.8.sp,
            )
        }
    }
}