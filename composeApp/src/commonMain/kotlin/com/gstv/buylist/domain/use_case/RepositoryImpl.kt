package com.gstv.buylist.domain.use_case

import com.example.database.AppDatabase
import com.example.database.Items
import com.example.database.Lists
import com.gstv.buylist.domain.BuyListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
import com.example.database.Lists as DataBaseList

class RepositoryImpl(
    database: AppDatabase
) : BuyListRepository {

    private val queries = database.appDatabaseQueries

    override suspend fun getLists(): List<DataBaseList> = withContext(Dispatchers.IO) {
        queries.selectAllLists().executeAsList()
    }

    override suspend fun insertList(list: Lists) {
        queries.insertList(
            title = list.title,
            description = list.description
        )
    }

    override suspend fun getItemsByListId(listId: String): List<Items> =
        withContext(Dispatchers.IO) {
            queries.selectItemsByListId(listId).executeAsList()
        }
}