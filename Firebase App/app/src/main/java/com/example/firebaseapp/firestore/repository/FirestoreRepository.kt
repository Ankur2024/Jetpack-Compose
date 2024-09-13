package com.example.firebaseapp.firestore.repository

import com.example.firebaseapp.firestore.FirestoreModelResponse
import com.example.firebaseapp.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface FirestoreRepository {
    fun insert(
        item: FirestoreModelResponse.FirestoreItem
    ): Flow<ResultState<String>>

    fun getItems(): Flow<ResultState<List<FirestoreModelResponse>>>

    fun delete(key: String): Flow<ResultState<String>>

    fun update(item: FirestoreModelResponse): Flow<ResultState<String>>
}