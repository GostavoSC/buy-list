package com.gstv.buylist.domain.mapper

import com.example.database.Items
import com.gstv.buylist.model.Item

class ItemsToItemMapper : ModelMapper<List<Items>, List<Item>> {
    override fun invoke(from: List<Items>): List<Item> {
        if (from.isEmpty()) return emptyList()
        return from.map {
            Item(
                itemId = it.item_id,
                listId = it.list_id,
                title = it.title,
                isChecked = it.is_checked.toInt() == 1,
                description = it.description
            )
        }
    }
}