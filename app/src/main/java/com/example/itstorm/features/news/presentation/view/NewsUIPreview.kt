package com.example.itstorm.features.news.presentation.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.example.itstorm.core.domain.models.newsmodel.NewsCategory
import com.example.itstorm.core.ui.models.UINews
import com.example.itstorm.core.ui.theme.ITStorm_AuthenticationScreenTheme
import com.example.itstorm.features.news.presentation.store.NewsStore
import kotlinx.coroutines.flow.MutableStateFlow

@Preview(
    showSystemUi = true,
    showBackground = true,
)
@Composable
fun NewsUIPreview() {
    ITStorm_AuthenticationScreenTheme {
        val fakeComponent = object : NewsComponent {
            override val model = MutableStateFlow(
                NewsStore.State(
                    news = listOf(
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
                    focusedArticle = null
                )
            )
            override val slot: Value<ChildSlot<Unit, NewsComponent.Child>> =
                MutableValue(ChildSlot())

            override fun onArticleSelect(id: Long) {}
            override fun onToggleFavorite(id: Long) {}
            override fun onToggleRead(id: Long) {}
            override fun onClickWeather() {}
            override fun onClickFavorites() {}
        }

        NewsUI(component = fakeComponent)
    }
}
