package com.example.daggerhillt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.daggerhillt.daggerhilt.demo.Car
import com.example.daggerhillt.daggerhilt.demo.Main
import com.example.daggerhillt.ui.theme.DaggerHilltTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

//Base App Object is created by this annotation
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
//    @Inject
////    lateinit var computer: Computer
//    @Inject
//    lateinit var mainOne: MainOne
//    @Inject
//    lateinit var mainTwo: MainTwo
//    @Inject
//    lateinit var test: Test

    //Dagger hilt
    @Inject lateinit var car: Car
    @Inject lateinit var main: Main
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DaggerHilltTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    computer.getComputer()
//                    BaseApp().main.main()
//                    mainOne.demoOne()
//                    mainTwo.mainTwo()
//                    test.getName()

                    car.getCar()
                    main.getName()
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
//    companion object{
//        val FName = "Ankur"
//        val LName =  "Gupta"
//    }
}

@Composable
fun Greeting( modifier: Modifier = Modifier) {

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DaggerHilltTheme {
    }
}