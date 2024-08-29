package com.example.daggerhillt.manualdependecy.di

import android.util.Log

class Car constructor(private val engine: Engine, private val wheel: Wheel){
    fun getCar(){
        engine.getEngine()
        wheel.getWheel()
        Log.d("main", "Car is running")
    }
}
class Engine {
    fun getEngine(){
        Log.d("main", "Engine Started")
    }
}
class Wheel {
    fun getWheel(){
        Log.d("main", "wheel is round")
    }
}