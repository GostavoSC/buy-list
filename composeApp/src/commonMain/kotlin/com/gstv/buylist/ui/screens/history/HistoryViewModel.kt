package com.gstv.buylist.ui.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gstv.buylist.domain.use_case.ItemsUseCase
import com.gstv.buylist.domain.use_case.ListsUseCase
import com.gstv.buylist.domain.use_case.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val listRepository: ListsUseCase,
    private val itemUseCase: ItemsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(HistoryState())
    val state = _state.asStateFlow()

    init {
        fetchLists()
        fetchItems()
    }

    private fun fetchLists() {
        viewModelScope.launch {
            when (val result = listRepository.getLists()) {
                is Result.Error -> Unit
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            list = result.data
                        )
                    }
                }
            }
        }
    }

    private fun fetchItems() {
        viewModelScope.launch {
            _state.value.list.forEach { currentList ->
                when (val result = itemUseCase.getItemsByListId(currentList.id)) {
                    is Result.Error -> Unit
                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                list = it.list.map { list ->
                                    if (list.id == currentList.id) list.copy(
                                        items = result.data
                                    ) else list
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}