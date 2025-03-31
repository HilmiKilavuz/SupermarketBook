package com.kilavuzhilmi.supermarketbook.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kilavuzhilmi.supermarketbook.model.Item

@Dao
interface ItemDao {
    @Query("Select name , id FROM Item")
    fun getItemNameAndId(): List<Item>

    @Query("Select * FROM Item WHERE id=:id")
    fun getItemById(id: Int): Item

    @Insert
   suspend fun insert(item: Item)

    @Delete
   suspend fun delete(item: Item)
}