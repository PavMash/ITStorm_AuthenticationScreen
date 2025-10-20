package com.example.itstorm.core.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.itstorm.R

@Composable
fun LogoImage(
    modifier: Modifier = Modifier
) {
    AsyncImage(
        modifier = modifier,
        model = ImageRequest.Builder(LocalContext.current)
            .data(R.raw.logo)
            .decoderFactory(SvgDecoder.Factory())
            .build(),
        contentDescription = stringResource(R.string.logo_image_description)
    )
}