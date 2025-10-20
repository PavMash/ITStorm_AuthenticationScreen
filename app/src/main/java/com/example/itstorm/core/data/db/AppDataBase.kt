package com.example.itstorm.core.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.itstorm.core.data.db.converters.Converters
import com.example.itstorm.core.data.db.dao.NewsDao
import com.example.itstorm.core.data.db.entities.NewsEntity

@Database(entities = [NewsEntity::class], version = 3)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}