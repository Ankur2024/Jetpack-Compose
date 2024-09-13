package com.example.firebaseapp.common

import android.app.Dialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog

@Composable
fun CommonDialog(){
    Dialog(onDismissRequest = {  }) {
        CircularProgressIndicator()
    }
}