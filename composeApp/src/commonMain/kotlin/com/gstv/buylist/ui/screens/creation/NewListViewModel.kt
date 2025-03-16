package com.gstv.buylist.ui.screens.creation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gstv.buylist.domain.use_case.ItemsUseCase
import com.gstv.buylist.domain.use_case.ListsUseCase
import com.gstv.buylist.domain.use_case.Result
import com.gstv.buylist.model.Item
import com.gstv.buylist.model.Lists
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewListViewModel(
    private val listsUseCase: ListsUseCase,
    private val itemsUseCase: ItemsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(NewListState())
    val state = _state.asStateFlow()

    private val _events = MutableStateFlow<Events>(Events.Init)
    val events = _events.asSharedFlow()

    fun fetchList(listId: Long) {
        viewModelScope.launch {
            when (val result = listsUseCase.getListById(listId)) {
                is Result.Error -> emitEvent(Events.OnError(result.message))
                is Result.Success -> {
                    val items = fetchItems(listId)
                    _state.update {
                        it.copy(
                            list = result.data,
                            title = result.data.title,
                            description = result.data.description ?: "",
                            items = items,
                            uiState = UiNewListState.Saved
                        )
                    }
                }
            }
        }
    }

    private suspend fun fetchItems(listId: Long): List<Item> {

        when (val result = itemsUseCase.getItemsByListId(listId)) {
            is Result.Success -> {
                return result.data
            }

            is Result.Error -> emitEvent(Events.OnError(result.message))
        }

        return emptyList()

    }

    fun saveList() {
        viewModelScope.launch {
            val list = insertList()
            val id = list?.id ?: return@launch

            val items = updateDatabaseItems(id)

            _state.update {
                it.copy(
                    list = list,
                    items = items,
                    uiState = UiNewListState.Saved
                )
            }
        }
    }

    private suspend fun updateDatabaseItems(listId: Long): List<Item> {
        when (val result =
            itemsUseCase.insertItems(_state.value.items, listId)) {
            is Result.Error -> {
                emitEvent(Events.OnError(result.message))
            }

            is Result.Success -> {
                return result.data
            }
        }

        return emptyList()
    }

    fun setIsEditing() {
        _state.update {
            it.copy(
                uiState = UiNewListState.Editing()
            )
        }
    }

    fun updateTitle(title: String) {
        _state.update {
            it.updateTitle(title)
        }
    }

    fun updateDescription(description: String) {
        _state.update {
            it.updateDescription(description = description)
        }
    }

    fun onBackPressed() {
        if (_state.value.uiState is UiNewListState.Editing) {
            emitEvent(Events.WarningClose)
        } else {
            emitEvent(Events.Close)
        }
    }

    fun updateItem(item: Item) {
        val isChecked = !item.isChecked
        _state.update {
            it.updateItem(
                item.copy(
                    isChecked = isChecked
                )
            )
        }
        item.itemId?.let{
            viewModelScope.launch {
                itemsUseCase.updateItemCheck(item.itemId, isChecked)
            }
        }
    }

    fun insertItem(item: String) {
        if (item.isEmpty()) return

        _state.update {
            it.insertItem(Item(title = item))
        }
    }

    private suspend fun insertList(): Lists? {
        if (_state.value.list != null) return _state.value.list

        when (val result = listsUseCase.insertList(
            Lists(
                title = state.value.title,
                description = state.value.description,
                items = emptyList()
            )
        )) {
            is Result.Success -> {
                return result.data
            }

            is Result.Error -> {
                emitEvent(Events.OnError(result.message))
            }
        }

        return null
    }

    private fun emitEvent(event: Events) {
        _events.tryEmit(event)
    }
}

sealed class Events {
    data class OnError(val message: String) : Events()
    data class Loading(val isLoading: Boolean) : Events()
    data object Close : Events()
    data object WarningClose : Events()
    data object Init : Events()
}
