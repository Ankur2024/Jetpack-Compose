package com.example.daggerhillt.di

import android.util.Log
import javax.inject.Inject

class Computer @Inject constructor(private val ram: Ram, private val hardDrive: HardDrive) {
    fun getComputer(){
        ram.getRam()
        hardDrive.getMemory()
        Log.d("main", "getComputer")
    }
}

//val computer  = Computer() ==> @Inject

class Ram @Inject constructor() {
    fun getRam(){
        Log.d("main", "Ram")
    }
}

class HardDrive @Inject constructor() {
    fun getMemory(){
        Log.d("main", "Hard Drive")
    }
}