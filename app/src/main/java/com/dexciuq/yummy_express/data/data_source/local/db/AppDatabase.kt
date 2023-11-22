package com.dexciuq.yummy_express.data.data_source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dexciuq.yummy_express.data.model.local.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}