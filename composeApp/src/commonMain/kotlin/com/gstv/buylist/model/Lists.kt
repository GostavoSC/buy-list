package com.gstv.buylist.model

import kotlinx.serialization.Serializable


@Serializable
data class Lists(
    val id: Long = 0,
    val title: String,
    val description: String?,
    val items: List<Item> = emptyList()
)