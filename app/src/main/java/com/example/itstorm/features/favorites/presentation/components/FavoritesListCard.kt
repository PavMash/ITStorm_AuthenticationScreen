package com.example.itstorm.features.favorites.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.itstorm.R
import com.example.itstorm.core.ui.components.ChipCard
import com.example.itstorm.core.ui.components.SubtitleText
import com.example.itstorm.core.ui.models.UINews
import com.example.itstorm.core.ui.theme.Black
import com.example.itstorm.core.ui.theme.Grey44
import com.example.itstorm.core.ui.theme.White
import com.example.itstorm.core.utils.categoryToUI

@Composable
fun FavoritesListCard(
    modifier: Modifier = Modifier,
    content: UINews,
    toRemoveList: List<Long>,
    onToggleFavorite: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(6.dp),
        border = BorderStroke(
            width = 1.dp,
            color = Grey44
        ),
        colors = CardDefaults.cardColors(
            containerColor = Black
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                modifier = Modifier.size(64.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(6.dp))
                    .background(White),
                painter = rememberAsyncImagePainter(content.previewImagePath),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.width(202.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                SubtitleText(
                    text = content.title,
                    color = White,
                    maxLines = 1,
                )

                ChipCard(
                    text = categoryToUI(content.category)
                )
            }

            IconButton(
                onClick = onToggleFavorite
            ) {
                Icon(
                    painter = if (!toRemoveList.contains(content.id))
                        painterResource(R.drawable.heart)
                    else painterResource(R.drawable.notfavorite),
                    contentDescription = null,
                    tint = White
                )
            }
        }
    }
}