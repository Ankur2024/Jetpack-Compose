package com.example.daggerhillt.interfaces

import android.util.Log
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

interface DemonOne{
    fun demoOne()
}

class DemoImplementationOne @Inject constructor(): DemonOne{
    override fun demoOne() {
        Log.d("main", "demoOne is calling ..")
    }

}


class MainOne @Inject constructor(private val demonOne: DemonOne){
    fun demoOne(){
        demonOne.demoOne()
    }
}


@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule2{
    @Binds
    @Singleton
    abstract fun providesOne(
        demoImplementationOne: DemoImplementationOne
    ): DemonOne
}