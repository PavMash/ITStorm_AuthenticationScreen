package com.example.itstorm.features.authentication.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.itstorm.R
import com.example.itstorm.core.ui.components.LogoImage
import com.example.itstorm.core.ui.components.TitleText
import com.example.itstorm.core.ui.theme.GreyE5

@Composable
fun TextWithLogo(
    modifier: Modifier = Modifier,
    text: String
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(
            space = 2.dp,
            alignment = Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TitleText(
            text = text,
            color = GreyE5
        )

        LogoImage(modifier = Modifier.size(48.dp))
    }
}