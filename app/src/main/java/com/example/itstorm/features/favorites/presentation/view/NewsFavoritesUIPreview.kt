package com.example.itstorm.features.favorites.presentation.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.itstorm.core.domain.models.newsmodel.NewsCategory
import com.example.itstorm.core.ui.models.UINews
import com.example.itstorm.core.ui.theme.ITStorm_AuthenticationScreenTheme
import com.example.itstorm.features.favorites.presentation.store.NewsFavoritesStore
import kotlinx.coroutines.flow.MutableStateFlow

@Preview(
    showSystemUi = true,
    showBackground = true,
)
@Composable
fun NewsFavoritesUIPreview() {
    ITStorm_AuthenticationScreenTheme {
        val fakeComponent = object : NewsFavoritesComponent {
            override val model = MutableStateFlow(
                NewsFavoritesStore.State(
                    filtered = listOf(
                        UINews(
                            id = 1,
                            title = "Тайные улочки Барселоны",
                            content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor",
                            previewImagePath = "file:///android_asset/news_images/spanish_view.png",
                            category = NewsCategory.Culture,
                            type = "Статья",
                            timeSinceCreation = "5 минут назад",
                            timeToRead = "15 мин",
                            isRead = false,
                            isFavorite = false
                        ),
                        UINews(
                            id = 2,
                            title = "Как проходит рабочий день PM из маленькой инди-компании?",
                            content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor",
                            previewImagePath = "file:///android_asset/news_images/technological_company.png",
                            category = NewsCategory.Technology,
                            type = "Эссе",
                            timeSinceCreation = "3 часа назад",
                            timeToRead = "45 мин",
                            isRead = true,
                            isFavorite = true
                        )
                    ),
                    toRemove = emptyList(),
                    appliedFilter = null
                )
            )

            override fun onClickWeather() {}
            override fun onClickNews() {}
            override fun onFilterNews(filter: NewsCategory?) {}
            override fun onToggleFavorite(id: Long) {}
        }

        NewsFavoritesUI(component = fakeComponent)
    }
}
