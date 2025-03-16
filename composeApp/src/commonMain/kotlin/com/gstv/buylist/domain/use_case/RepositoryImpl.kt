package com.gstv.buylist.domain.use_case

import com.example.database.AppDatabase
import com.example.database.Items
import com.example.database.Lists
import com.gstv.buylist.domain.BuyListRepository
import com.gstv.buylist.model.Item
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

    override suspend fun insertList(list: Lists): Lists {
        queries.insertList(
            title = list.title,
            description = list.description
        )

        return queries.getLastInsertedList().executeAsOne()
    }

    override suspend fun insertItem(item: Item): List<Items> {
        val id = item.listId ?: return emptyList()
        queries.insertItem(
            list_id = id,
            title = item.title,
            is_checked = if (item.isChecked) 1 else 0
        )

        return queries.selectItemsByListId(id).executeAsList()
    }

    override suspend fun insertItems(items: List<Item>, listId: Long): List<Items> {
        queries.transaction {
            items.forEach { item ->
                if (item.itemId == null){
                    queries.insertItem(
                        list_id = listId,
                        title = item.title,
                        is_checked = if (item.isChecked) 1 else 0
                    )
                } else{
                    queries.updateItem(
                        item_id = item.itemId,
                        title = item.title,
                        is_checked = if (item.isChecked) 1 else 0,
                        description = item.description
                    )
                }
            }
        }
        return queries.selectItemsByListId(listId).executeAsList()
    }

    override suspend fun getItemsByListId(listId: Long): List<Items> =
        withContext(Dispatchers.IO) {
            queries.selectItemsByListId(listId).executeAsList()
        }

    override suspend fun updateItemCheck(itemId: Long, isChecked: Boolean) {
        return queries.updateItemCheck(item_id = itemId, is_checked = if (isChecked) 1 else 0)
    }

    override suspend fun deleteList(id: Long) {
        return queries.deleteList(id)
    }

    override suspend fun getListById(listId: Long): Lists {
        return queries.selectListById(listId).executeAsOne()
    }
}