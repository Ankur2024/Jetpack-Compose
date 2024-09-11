package com.example.daggerhillt.qualifiers

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.daggerhillt.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

class Test @Inject constructor(
    @FName
    val fName: String,
    @LName
    val lName: String,
    @ActivityContext
    val context: Context
){
    fun getName(){
        Log.d("main", "$fName $lName")
        Toast.makeText(context, "Hello", Toast.LENGTH_LONG).show()
    }
}

//@Module
//@InstallIn(SingletonComponent::class)
//object AppModule_3{
//    @Provides
//    @FName
//    fun providesFName() = "Ankur"
//    @Provides
//    @LName
//    fun providesLName() = "Gupta"
//}

@Module
@InstallIn(SingletonComponent::class)
object AppModule_3{
    @Provides
    @FName
    fun providesFName() = MainActivity.FName
    @Provides
    @LName
    fun providesLName() = MainActivity.LName
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FName

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LName