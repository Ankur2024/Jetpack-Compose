package com.example.firebaseapp.feature.firebase_auth.repository

import android.app.Activity
import com.example.firebaseapp.feature.firebase_auth.AuthUser
import com.example.firebaseapp.utils.ResultState
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun createUser(
        auth: AuthUser
    ): Flow<ResultState<String>>

    fun loginUser(
        auth: AuthUser
    ): Flow<ResultState<String>>

    fun createUserWithPhone(
        phone: String,
        activity: Activity
    ): Flow<ResultState<String>>

    fun signWithCredential(
        otp: String
    ): Flow<ResultState<String>>
}
