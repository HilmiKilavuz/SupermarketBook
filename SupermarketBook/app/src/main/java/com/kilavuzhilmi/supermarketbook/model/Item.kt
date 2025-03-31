package com.kilavuzhilmi.supermarketbook.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
class Item(
    @ColumnInfo("name")
    var itemname: String,
    @ColumnInfo("store")
    var storename: String?,
    @ColumnInfo("price")
    var price :String?,
    @ColumnInfo("image")
    var image: ByteArray?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

}