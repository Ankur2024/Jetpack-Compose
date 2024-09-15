package com.example.firebaseapp.feature.firebase_auth.ui

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.example.firebaseapp.feature.firebase_auth.AuthUser
import com.example.firebaseapp.feature.firebase_auth.repository.AuthRepository
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repo: AuthRepository
):ViewModel() {

    fun createUser(authUser: AuthUser) = repo.createUser(authUser)
    fun loginUser(authUser: AuthUser) = repo.loginUser(authUser)

    fun createUserWithPhone(
        mobile: String,
        acitivity: Activity
    ) = repo.createUserWithPhone(mobile, acitivity)
    fun signInWithCredential(
        code: String
    ) = repo.signWithCredential(code)
}