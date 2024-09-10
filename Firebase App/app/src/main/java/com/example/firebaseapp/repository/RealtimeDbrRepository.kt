package com.example.firebaseapp.repository

import com.example.firebaseapp.firebaserealtimedb.RealtimeModelResponse
import com.example.firebaseapp.utils.ResultState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RealtimeDbrRepository @Inject constructor(): RealtimeRepository {

    override fun insert(item: RealtimeModelResponse.RealtimeItems): Flow<Result<String>> {
        TODO("Not yet implemented")
    }

    override fun getItems(): Flow<ResultState<List<RealtimeModelResponse>>> {
        TODO("Not yet implemented")
    }

    override fun deleteItem(key: String): Flow<ResultState<String>> {
        TODO("Not yet implemented")
    }

    override fun updateItem(res: RealtimeModelResponse): Flow<ResultState<String>> {
        TODO("Not yet implemented")
    }
}