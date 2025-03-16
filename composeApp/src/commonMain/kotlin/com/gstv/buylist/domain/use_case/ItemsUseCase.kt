package com.gstv.buylist.domain.use_case

import com.example.database.Items
import com.gstv.buylist.domain.BuyListRepository
import com.gstv.buylist.domain.mapper.ModelMapper
import com.gstv.buylist.model.Item

class ItemsUseCase(
    private val repository: BuyListRepository,
    private val itemMapper: ModelMapper<List<Items>, List<Item>>,
) {

    suspend fun insertItems(items: List<Item>, listId: Long): Result<List<Item>> =
        executeWithResult {
            itemMapper(repository.insertItems(items, listId))
        }

    suspend fun updateItemCheck(itemId: Long, isChecked: Boolean) = executeWithResult {
        repository.updateItemCheck(itemId, isChecked)
    }

    suspend fun getItemsByListId(id: Long): Result<List<Item>> = executeWithResult {
        itemMapper(repository.getItemsByListId(id))
    }
}