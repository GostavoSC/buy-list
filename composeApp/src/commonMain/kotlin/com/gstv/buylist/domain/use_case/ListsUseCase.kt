package com.gstv.buylist.domain.use_case

import com.gstv.buylist.domain.BuyListRepository
import com.gstv.buylist.domain.mapper.ModelMapper
import com.gstv.buylist.model.Lists
import com.example.database.Lists as DataBaseList

class ListsUseCase(
    private val repository: BuyListRepository,
    private val listsMapper: ModelMapper<List<DataBaseList>, List<Lists>>,
    private val listMapper: ModelMapper<DataBaseList, Lists>,
    private val insertListMapper: ModelMapper<Lists, DataBaseList>
) {
    suspend fun getLists(): Result<List<Lists>> = executeWithResult {
        listsMapper(repository.getLists())
    }

    suspend fun getListById(listId: Long): Result<Lists> = executeWithResult {
        listMapper(repository.getListById(listId))
    }

    suspend fun insertList(list: Lists): Result<Lists> = executeWithResult {
        listMapper(repository.insertList(insertListMapper(list)))
    }

    suspend fun deleteList(id: Long): Result<Unit> = executeWithResult {
        repository.deleteList(id)
    }
}


sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}

inline fun <T> executeWithResult(action: () -> T): Result<T> {
    return try {
        Result.Success(action())
    } catch (t: Throwable) {
        Result.Error(t.message ?: "Unknown error")
    }
}