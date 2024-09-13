package com.example.firebaseapp.utils

import android.content.Context
import android.widget.Toast
import kotlin.time.Duration

fun Context.showMsg(
    msg: String,
    duration: Int = Toast.LENGTH_SHORT
) = Toast.makeText(this,msg,duration)