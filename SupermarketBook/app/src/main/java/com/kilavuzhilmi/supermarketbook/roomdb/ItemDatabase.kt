package com.kilavuzhilmi.supermarketbook.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kilavuzhilmi.supermarketbook.model.Item


@Database(entities = [Item::class], version = 1)
abstract class ItemDatabase : RoomDatabase() {
    abstract fun ItemDao(): ItemDao

}