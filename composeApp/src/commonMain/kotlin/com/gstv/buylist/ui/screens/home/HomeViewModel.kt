package com.gstv.buylist.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gstv.buylist.domain.use_case.ListsUseCase
import com.gstv.buylist.domain.use_case.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val listsUseCase: ListsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    init {
        fetchLists()
    }

    fun fetchLists() {
        viewModelScope.launch {
            when (val result = listsUseCase.getLists()) {
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            lists = result.data
                        )
                    }
                }

                is Result.Error -> {
                    // Handle error
                }

            }
        }
    }
}