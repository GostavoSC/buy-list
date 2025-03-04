package com.gstv.buylist.model

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val itemId: String,
    val listId: String,
    val title: String,
    val isChecked: Boolean
)