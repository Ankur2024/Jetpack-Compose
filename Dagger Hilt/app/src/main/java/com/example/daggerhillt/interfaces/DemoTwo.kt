package com.example.daggerhillt.interfaces

import android.util.Log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton


interface DemoTwo{
    fun demoTwo()
}

class DemoTwoImplementation @Inject constructor(): DemoTwo{
    override fun demoTwo() {
        Log.d("main", "Demo2 is calling...")
    }

}

class MainTwo @Inject constructor (private val demoTwo: DemoTwo){
    fun mainTwo(){
        demoTwo.demoTwo()
    }
}

@Module
@InstallIn(SingletonComponent::class)
object AppModule_3 {
    @Provides
    @Singleton
    fun provdiesDemoTwo(): DemoTwo = DemoTwoImplementation()
}