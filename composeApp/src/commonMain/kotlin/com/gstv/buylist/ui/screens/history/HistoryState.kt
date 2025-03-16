package com.gstv.buylist.ui.screens.history

import com.gstv.buylist.model.Lists

data class HistoryState(
    val list: List<Lists> = emptyList()
)
