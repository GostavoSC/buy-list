package com.gstv.buylist.domain.mapper

import com.example.database.Lists as DataBaseList
import com.gstv.buylist.model.Lists


class InsertListMapper : ModelMapper<Lists, DataBaseList> {

    override fun invoke(from: Lists) = DataBaseList(
        id = from.id,
        title = from.title,
        description = from.description
    )
}