package com.gstv.buylist.domain

import com.example.database.Items
import com.example.database.Lists
import com.gstv.buylist.model.Item


interface BuyListRepository {
    suspend fun getLists(): List<Lists>
    suspend fun insertList(list: Lists): Lists
    suspend fun insertItem(item: Item): List<Items>
    suspend fun insertItems(items: List<Item>, listId: Long): List<Items>
    suspend fun getItemsByListId(listId: Long): List<Items>
    suspend fun updateItemCheck(itemId: Long, isChecked: Boolean)
    suspend fun deleteList(id: Long)
    suspend fun getListById(listId: Long): Lists
}

