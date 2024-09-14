package com.example.daggerhillt.daggerhilt.demo

import android.util.Log
import javax.inject.Inject


class Car @Inject constructor(private val wheel: Wheel, private val engine: Engine) {

    fun getCar(){
        engine.getEngine()
        wheel.getWheel()
        Log.d("main", "Car is running..")
    }
}