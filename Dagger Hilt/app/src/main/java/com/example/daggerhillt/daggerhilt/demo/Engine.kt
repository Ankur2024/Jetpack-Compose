package com.example.daggerhillt.daggerhilt.demo

import android.util.Log
import javax.inject.Inject

class Engine @Inject constructor() {
    fun getEngine(){
        Log.d("main", "Enigne started..")
    }
}