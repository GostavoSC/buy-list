package com.gstv.buylist.ui.screens.home

import com.gstv.buylist.model.Lists

data class HomeState(
    val lists: List<Lists> = emptyList(),
)
