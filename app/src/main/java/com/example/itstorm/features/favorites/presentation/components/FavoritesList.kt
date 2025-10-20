package com.example.itstorm.features.favorites.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.itstorm.core.ui.models.UINews

@Composable
fun FavoritesList(
    modifier: Modifier = Modifier,
    newsList: List<UINews>,
    toRemoveList: List<Long>,
    onToggleFavorite: (Long) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(newsList) { item ->
            val onToggleFavoriteConcrete = { onToggleFavorite(item.id) }

            FavoritesListCard(
                content = item,
                toRemoveList = toRemoveList,
                onToggleFavorite = onToggleFavoriteConcrete
            )
        }
    }
}