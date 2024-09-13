package com.example.firebaseapp.firestore.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.firebaseapp.ui.theme.FirebaseAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirestoreActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FirebaseAppTheme{
                val isInput =  remember {
                    mutableStateOf(false)
                }
                Surface() {
                    Scaffold(
                        floatingActionButton = {
                            FloatingActionButton(onClick = { 
                                isInput.value = true
                            }) {
                                Icon(imageVector = Icons.Default.Add, contentDescription = "")
                            }
                        }
                    ) {
                        FirestoreScreen(isInput)
                    }
                }
            }
        }
    }
}