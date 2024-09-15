package com.example.firebaseapp.feature.firebase_auth.repository

import android.app.Activity
import android.util.Log
import android.util.TimeUtils
import com.example.firebaseapp.feature.firebase_auth.AuthUser
import com.example.firebaseapp.utils.ResultState
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDb: FirebaseAuth
): AuthRepository {
    private lateinit var omVerificationCode: String
    override fun createUser(auth: AuthUser): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)
        authDb.createUserWithEmailAndPassword(auth.email!!, auth.password!!)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    trySend(ResultState.Success("User Created Successfully"))
                } else {
                    trySend(ResultState.Failure(task.exception ?: Exception("Unknown Error")))
                    Log.e("FirebaseAuth", "Error: ${task.exception?.message}")
                }
            }
        awaitClose{
            close()
        }
    }

    override fun loginUser(auth: AuthUser): Flow<ResultState<String>> = callbackFlow{
        trySend(ResultState.Loading)
        authDb.signInWithEmailAndPassword(
            auth.email!!,
            auth.password!!
        ).addOnSuccessListener {
            trySend(ResultState.Success("Log in successfully"))
            Log.d("FirebaseAuth", "current user login id: ${authDb.currentUser?.uid}")

        }.addOnFailureListener {
            trySend(ResultState.Failure(it))
        }
        awaitClose{
            close()
        }
    }

    override fun createUserWithPhone(phone: String, activity: Activity): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        val onVerificationCallback = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                TODO("Not yet implemented")
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                trySend(ResultState.Failure(p0))
            }

            override fun onCodeSent(verificationCode: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(verificationCode, p1)
                trySend(ResultState.Success("OTP Send Successfully"))
                omVerificationCode = verificationCode
            }

        }
        val options = PhoneAuthOptions.newBuilder(authDb)
            .setPhoneNumber("+91$phone")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(onVerificationCallback)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        awaitClose{
            close()
        }

    }

    override fun signWithCredential(otp: String): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        val credential = PhoneAuthProvider.getCredential(omVerificationCode, otp)
        authDb.signInWithCredential(credential)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    trySend(ResultState.Success("OTP verified"))
                }
            }.addOnFailureListener {
                trySend(ResultState.Failure(it))
            }
        awaitClose{
            close()
        }
    }
}