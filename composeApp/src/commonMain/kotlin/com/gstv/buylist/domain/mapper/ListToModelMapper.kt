package com.gstv.buylist.domain.mapper

import com.example.database.Lists as DataBaseList
import com.gstv.buylist.model.Lists

class ListToModelMapper : ModelMapper<DataBaseList, Lists> {
    override fun invoke(from: DataBaseList): Lists {

        return Lists(
            id = from.id,
            title = from.title,
            description = from.description,
            emptyList()
        )
    }

}