package com.gstv.buylist.ui.screens.creation

import com.gstv.buylist.model.Item
import com.gstv.buylist.model.Lists

data class NewListState(
    val list: Lists? = null,
    val title: String = "Title",
    val description: String = "Description",
    val items: List<Item> = emptyList(),
    val uiState: UiNewListState = UiNewListState.Editing()
) {

    private fun isTitleValid(): Boolean = title != "Title"

    fun updateTitle(title: String) = copy(
        title = title,
        uiState = UiNewListState.Editing(hasChanges = true)
    ).setHasChanges()

    fun updateDescription(description: String) = copy(
        description = description
    ).setHasChanges()

    fun insertItem(item: Item) = copy(
        items = items.plus(item)
    ).setHasChanges()


    private fun setHasChanges() = copy(
        uiState = UiNewListState.Editing(hasChanges = true, canSave = isTitleValid())
    )

    fun updateItem(item: Item) = copy(
        items = items.map {
            if (it.title == item.title) item else it
        }
    )
}

sealed interface UiNewListState {
    data class Editing(
        val canSave: Boolean = false,
        val hasChanges: Boolean = false
    ) : UiNewListState

    data object Saved : UiNewListState
}


