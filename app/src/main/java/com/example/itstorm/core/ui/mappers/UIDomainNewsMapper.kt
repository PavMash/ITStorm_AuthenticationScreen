package com.example.itstorm.core.ui.mappers

import com.example.itstorm.core.domain.models.newsmodel.DomainNews
import com.example.itstorm.core.domain.models.newsmodel.NewsType
import com.example.itstorm.core.ui.models.UINews
import java.time.Duration
import java.time.Instant

fun DomainNews.toUI(): UINews = UINews(
    id = id,
    title = title,
    content = content,
    previewImagePath = previewImagePath,
    type = newsTypeToDisplay(type),
    category = category,
    timeToRead = formatTimeToRead(timeToRead),
    timeSinceCreation = formatTimeSinceCreation(createdAt),
    isRead = isRead,
    isFavorite = isFavorite
)

private fun newsTypeToDisplay(type: NewsType): String =
    when(type) {
        (NewsType.Article) -> "Статья"
        (NewsType.Essay) -> "Эссе"
        (NewsType.Blog) -> "Блог"
    }

private fun formatTimeToRead(time: Int): String =
    when {
        time < 60 -> "$time мин"
        else -> "${time/60} ч, ${time%60} мин"
    }

fun formatTimeSinceCreation(createdAt: Instant): String {
    val duration = Duration.between(createdAt, Instant.now())

    val timePassed = when {
        (duration.toMinutes() < 1) -> "только что"
        (duration.toMinutes() == 1L) -> "${duration.toMinutes()} минуту назад"
        (duration.toMinutes() < 5) -> "${duration.toMinutes()} минуты назад"
        (duration.toMinutes() < 60) -> "${duration.toMinutes()} минут назад"
        (duration.toHours() == 1L) -> "${duration.toHours()} час назад"
        (duration.toHours() < 5) -> "${duration.toHours()} часа назад"
        (duration.toHours() < 24) -> "${duration.toHours()} часов назад"
        (duration.toDays() == 1L) -> "${duration.toDays()} день назад"
        (duration.toDays() < 5) -> "${duration.toDays()} дня назад"
        else -> "${duration.toDays()} дней назад"
    }

    return timePassed
}