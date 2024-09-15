package com.example.firebaseapp.feature.firebase_auth.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import com.google.rpc.context.AttributeContext.Auth
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@AndroidEntryPoint
class AuthAcitivity: ComponentActivity()  {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            Surface {
                Scaffold {
//                    AuthScreen()
                    PhoneAuthScreen(activity = this)
                }
            }
        }
    }
}