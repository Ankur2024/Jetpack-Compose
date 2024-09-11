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
import com.example.daggerhillt.di.Computer
import com.example.daggerhillt.interfaces.ImplementationOne
import com.example.daggerhillt.interfaces.Main
import com.example.daggerhillt.interfaces.MainOne
import com.example.daggerhillt.interfaces.MainTwo
import com.example.daggerhillt.manualdependecy.BaseApp
import com.example.daggerhillt.qualifiers.FName
import com.example.daggerhillt.qualifiers.Test
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
    @Inject
    lateinit var test: Test
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
                    test.getName()

                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
    companion object{
        val FName = "Ankur"
        val LName =  "Gupta"
    }
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