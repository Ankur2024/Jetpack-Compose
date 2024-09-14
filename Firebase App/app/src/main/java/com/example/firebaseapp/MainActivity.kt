package com.example.firebaseapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.firebaseapp.firebaserealtimedb.ui.RealtimeScreen
import com.example.firebaseapp.ui.theme.FirebaseAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FirebaseAppTheme {
                val isInsert = remember { mutableStateOf(false) }
                Scaffold(
                    floatingActionButton = {
                        FloatingActionButton(onClick = {
                            isInsert.value = true
                        }) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Icon"
                            )
                        }
                    }
                ) { innerPadding ->
                    RealtimeScreen(
                        modifier = Modifier.padding(innerPadding),
                        isInsert = isInsert
                    )
                }
            }
        }
    }
}
