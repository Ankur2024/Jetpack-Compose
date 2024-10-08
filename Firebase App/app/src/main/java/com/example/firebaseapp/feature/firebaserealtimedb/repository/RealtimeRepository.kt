package com.example.firebaseapp.feature.firebaserealtimedb.repository

import com.example.firebaseapp.feature.firebaserealtimedb.RealtimeModelResponse
import com.example.firebaseapp.utils.ResultState
import kotlinx.coroutines.flow.Flow


interface RealtimeRepository {
    fun insert(item: RealtimeModelResponse.RealtimeItems): Flow<ResultState<String>>
    fun getItems(): Flow<ResultState<List<RealtimeModelResponse>>>
    fun deleteItem(key: String): Flow<ResultState<String>>
    fun updateItem(res: RealtimeModelResponse): Flow<ResultState<String>>
}