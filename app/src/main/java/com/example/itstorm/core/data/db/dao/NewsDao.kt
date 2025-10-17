package com.example.itstorm.core.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.itstorm.core.data.db.entities.NewsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {
    @Query("SELECT * FROM news")
    fun getAll(): Flow<List<NewsEntity>>

    @Query("SELECT * FROM news WHERE isFavorite = 1")
    fun getFavorites(): Flow<List<NewsEntity>>

    @Query("SELECT * FROM news WHERE id = :id")
    suspend fun getNewsById(id: Long): NewsEntity?

    @Upsert
    suspend fun insertAll(news: List<NewsEntity>)

    @Update
    suspend fun update(updatedNews: NewsEntity)
}