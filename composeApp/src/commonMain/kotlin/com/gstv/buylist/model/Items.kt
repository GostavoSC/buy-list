package com.gstv.buylist.model

import kotlinx.serialization.Serializable

@Serializable
data class Item(
    val itemId: Long? = null,
    val listId: Long ? = null,
    val title: String,
    val description: String? = null,
    val isChecked: Boolean = false
)