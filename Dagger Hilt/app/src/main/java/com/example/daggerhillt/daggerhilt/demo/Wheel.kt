package com.example.daggerhillt.daggerhilt.demo

import android.util.Log
import javax.inject.Inject

class Wheel  @Inject constructor(){
    fun getWheel(){
        Log.d("main", "Wheel is ratating..")
    }
}