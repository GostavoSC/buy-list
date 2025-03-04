package com.gstv.buylist.domain.mapper

import com.example.database.Lists as DataBaseList
import com.gstv.buylist.model.Lists

class DatabaseListToListModelMapper : ModelMapper<List<DataBaseList>, List<Lists>> {
    override fun invoke(from: List<DataBaseList>): List<Lists> {
        if (from.isEmpty()) return emptyList()

        return from.map {
            Lists(
                id = it.id,
                title = it.title,
                description = it.description
            )
        }
    }
}