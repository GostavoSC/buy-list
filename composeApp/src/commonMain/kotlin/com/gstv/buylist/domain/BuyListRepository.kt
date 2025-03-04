package com.gstv.buylist.domain

import com.example.database.Items
import com.example.database.Lists


interface BuyListRepository {
    suspend fun getLists(): List<Lists>
    suspend fun insertList(list: Lists)
    suspend fun getItemsByListId(listId: String): List<Items>
}

