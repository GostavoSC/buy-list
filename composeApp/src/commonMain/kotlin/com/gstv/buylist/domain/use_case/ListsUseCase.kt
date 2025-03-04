package com.gstv.buylist.domain.use_case

import com.gstv.buylist.domain.BuyListRepository
import com.gstv.buylist.domain.mapper.ModelMapper
import com.gstv.buylist.model.Lists
import com.example.database.Lists as DataBaseList

class ListsUseCase(
    private val repository: BuyListRepository,
    private val listMapper: ModelMapper<List<DataBaseList>, List<Lists>>,
    private val insertListMapper: ModelMapper<Lists, DataBaseList>
) {
    suspend fun getLists(): ListResult {
        try {
            val result = listMapper(repository.getLists())

            return ListResult.Success(result)
        } catch (t: Throwable) {
            return ListResult.Error(t.message ?: "Unknown error")
        }
    }

    suspend fun insertList(list: Lists): ListResult {
        try {
            repository.insertList(insertListMapper(list))
            return ListResult.SuccessNoBody
        } catch (t: Throwable) {
            return ListResult.Error(t.message ?: "Unknown error")
        }
    }

}

sealed class ListResult {
    data class Success(val lists: List<Lists>) : ListResult()
    data object SuccessNoBody : ListResult()
    data class Error(val message: String) : ListResult()
}