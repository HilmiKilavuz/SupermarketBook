package com.kilavuzhilmi.supermarketbook.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.kilavuzhilmi.supermarketbook.model.Item
import com.kilavuzhilmi.supermarketbook.roomdb.ItemDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemViewModel(application: Application): AndroidViewModel(application) {
    val db = Room.databaseBuilder(
        application.applicationContext,
        ItemDatabase::class.java, "Items"
    ).build()
    private val itemDao = db.ItemDao()
    val itemList = mutableStateOf<List<Item>>(listOf())
    val selectedItem = mutableStateOf<Item?>(Item("","","",null))

    fun getItemList(){
        viewModelScope.launch(Dispatchers.IO) {
            itemList.value=itemDao.getItemNameAndId()
        }
    }

    fun getItemById(id:Int){
        viewModelScope.launch (Dispatchers.IO){
            selectedItem.value=itemDao.getItemById(id)
        }
    }

    fun saveItem(item: Item){
        viewModelScope.launch(Dispatchers.IO) {
            itemDao.insert(item)
        }

    }
    fun deleteItem(item: Item?){
        viewModelScope.launch(Dispatchers.IO) {
            item.let {
                itemDao.delete(item!!)
            }

        }

    }


}