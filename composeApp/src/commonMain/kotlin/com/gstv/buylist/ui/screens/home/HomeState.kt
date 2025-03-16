package com.gstv.buylist.ui.screens.home

import com.gstv.buylist.model.Lists

data class HomeState(
    val lists: List<Lists> = emptyList(),
) {
    fun getRecentList(): List<Lists> {
        return if (lists.size > 3)
            lists.subList(0, 3)
        else
            lists
    }
}
