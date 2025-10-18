package com.example.itstorm.features.weather.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.itstorm.R
import com.example.itstorm.core.ui.theme.Grey1A
import com.example.itstorm.core.ui.theme.Grey34
import com.example.itstorm.core.ui.theme.White
import com.example.itstorm.core.ui.theme.robotoFlexFontFamily

@Composable
fun EstimationCard(
    belongsToHistorySection: Boolean,
    city: String,
    temperature: Int,
    verdict: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        //modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Grey1A,
            disabledContainerColor = Grey1A
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            EstimationCardContent(city, temperature,
                PaddingValues(bottom = 10.dp))

            if (!belongsToHistorySection) {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = Grey34
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = stringResource(R.string.verdict_template) + "$city $verdict",
                    fontFamily = robotoFlexFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight(500),
                    color = White
                )
            }
        }
    }
}