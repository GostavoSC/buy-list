package com.gstv.buylist.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Lists(
    @SerialName("id")
    val id: Long = 0,
    @SerialName("title")
    val title: String,
    @SerialName("description")
    val description: String?,
)