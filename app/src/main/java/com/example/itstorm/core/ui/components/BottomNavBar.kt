package com.example.itstorm.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.itstorm.R

@Composable
fun BottomNavBar(
    hostingScreen: HostingScreen,
    onWeatherClick: () -> Unit,
    onNewsClick: () -> Unit,
    onFavoritesClick: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()
        .height(IntrinsicSize.Min)
        .padding(horizontal = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(
            6.dp,
            Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavButton(
            painter = painterResource(R.drawable.weather),
            label = stringResource(R.string.weather_page_navigation_text),
            enabled = (hostingScreen != HostingScreen.Weather),
            modifier = Modifier.weight(1f).fillMaxHeight()
        ) { onWeatherClick() }

        BottomNavButton(
            painter = painterResource(R.drawable.news),
            label = stringResource(R.string.news_page_navigation_text),
            enabled = (hostingScreen != HostingScreen.News),
            modifier = Modifier.weight(1f).fillMaxHeight()
        ) { onNewsClick() }

        BottomNavButton(
            painter = painterResource(R.drawable.heart),
            label = stringResource(R.string.favourites_page_navigation_text),
            enabled = (hostingScreen != HostingScreen.Favorites),
            modifier = Modifier.weight(1f).fillMaxHeight(),
        ) { onFavoritesClick() }
    }
}

