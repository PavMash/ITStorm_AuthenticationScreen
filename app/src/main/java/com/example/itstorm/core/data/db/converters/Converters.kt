package com.example.itstorm.core.data.db.converters

import android.net.Uri
import androidx.room.TypeConverter
import androidx.core.net.toUri
import com.example.itstorm.core.domain.models.newsmodel.NewsCategory
import com.example.itstorm.core.domain.models.newsmodel.NewsType
import java.time.Instant

class Converters {
    @TypeConverter
    fun instantToLong(instant: Instant?): Long? = instant?.toEpochMilli()

    @TypeConverter
    fun longToInstant(timestamp: Long?): Instant? =
        timestamp?.let { Instant.ofEpochMilli(it) }

    @TypeConverter
    fun newsTypeToString(type: NewsType?): String? = type?.name

    @TypeConverter
    fun stringToNewsType(str: String?): NewsType? =
        str?.let { NewsType.valueOf(it) }

    @TypeConverter
    fun newsCategoryToString(type: NewsCategory?): String? = type?.name

    @TypeConverter
    fun stringToNewsCategory(str: String?): NewsCategory? =
        str?.let { NewsCategory.valueOf(it) }
}