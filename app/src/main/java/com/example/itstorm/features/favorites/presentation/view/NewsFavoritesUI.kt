package com.example.itstorm.features.favorites.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.itstorm.R
import com.example.itstorm.core.ui.components.BottomNavBar
import com.example.itstorm.core.ui.components.HostingScreen
import com.example.itstorm.core.ui.components.SectionTitle
import com.example.itstorm.core.ui.theme.Black
import com.example.itstorm.core.ui.theme.ITStorm_AuthenticationScreenTheme
import com.example.itstorm.features.favorites.presentation.components.CategoryButtonBar
import com.example.itstorm.features.favorites.presentation.components.FavoritesList

@Composable
fun NewsFavoritesUI(component: NewsFavoritesComponent) {
    ITStorm_AuthenticationScreenTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Black,
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier.fillMaxWidth()
                        .windowInsetsPadding(
                            WindowInsets.navigationBars),
                    containerColor = Black
                ) {
                    BottomNavBar(
                        hostingScreen = HostingScreen.Favorites,
                        onWeatherClick = component::onClickWeather,
                        onNewsClick = component::onClickNews,
                        onFavoritesClick = {}
                    )
                }
            }
        ) { innerPadding ->
            NewsFavoritesScreen(component, innerPadding)
        }
    }
}

@Composable private fun NewsFavoritesScreen(
    component: NewsFavoritesComponent,
    innerPadding: PaddingValues
) {
    val state by component.model.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SectionTitle(
            text = stringResource(R.string.favourites_page_navigation_text),
            modifier = Modifier.align(Alignment.Start)
                .padding(start = 17.dp, top = 38.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        CategoryButtonBar(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 8.dp),
            onFilterClick = component::onFilterNews,
            currentFilter = state.appliedFilter
        )

        Spacer(modifier = Modifier.height(6.dp))

        FavoritesList(
            modifier = Modifier.padding(horizontal = 7.dp),
            newsList = state.filtered,
            toRemoveList = state.toRemove,
            onToggleFavorite = component::onToggleFavorite
        )
    }
}