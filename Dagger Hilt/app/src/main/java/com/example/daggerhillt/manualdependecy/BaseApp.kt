package com.example.daggerhillt.manualdependecy

import android.app.Application
import com.example.daggerhillt.interfaces.AppModule
import com.example.daggerhillt.manualdependecy.di.Car
import com.example.daggerhillt.manualdependecy.di.Engine
import com.example.daggerhillt.manualdependecy.di.Wheel

class BaseApp: Application() {

    var car = Car(Engine(), Wheel())

    val main = AppModule.main
}