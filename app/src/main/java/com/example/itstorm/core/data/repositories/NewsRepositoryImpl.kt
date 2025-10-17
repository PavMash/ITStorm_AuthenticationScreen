package com.example.itstorm.core.data.repositories

import com.example.itstorm.core.data.db.dao.NewsDao
import com.example.itstorm.core.data.db.entities.NewsEntity
import com.example.itstorm.core.data.mappers.toDomain
import com.example.itstorm.core.data.mappers.toEntity
import com.example.itstorm.core.domain.models.newsmodel.DomainNews
import com.example.itstorm.core.domain.repositories.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewsRepositoryImpl(
    private val newsDao: NewsDao
): NewsRepository {

    override fun getAllNews(): Flow<List<DomainNews>> =
        newsDao.getAll().map { it.map(NewsEntity::toDomain) }

    override fun getFavoriteNews(): Flow<List<DomainNews>> =
        newsDao.getFavorites().map { it.map(NewsEntity::toDomain) }

    override suspend fun getNewsById(id: Long): DomainNews? =
        newsDao.getNewsById(id)?.toDomain()

    override suspend fun preloadNews(initialNews: List<DomainNews>) =
        newsDao.insertAll(initialNews.map(DomainNews::toEntity))

    override suspend fun updateFavoriteStatus(id: Long, isFavorite: Boolean) {
        val entity = newsDao.getNewsById(id)
        entity?.let {
            newsDao.update(entity.copy(isFavorite = isFavorite))
        }
    }

    override suspend fun updateReadStatus(id: Long, isRead: Boolean) {
        val entity = newsDao.getNewsById(id)
        entity?.let {
            newsDao.update(entity.copy(isRead = isRead))
        }
    }
}