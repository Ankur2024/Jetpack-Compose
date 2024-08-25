package com.example.coroutine

import android.annotation.SuppressLint
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.coroutine.ui.theme.CoroutineTheme
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoroutineTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Greeting(modifier: Modifier = Modifier) {
//    GlobalScope.launch(Dispatchers.Main) {
//       val networkCall = doNetworkCall()
//       val networkCall2 = doNetworkCall2()
//        Log.d("Main Activity", networkCall)
//        Log.d("Main Activity", networkCall2)
//    }

    GlobalScope.launch (Dispatchers.Main){
        delay(100L)
    }
    runBlocking {
        delay(5000L )

    }
}

suspend fun doNetworkCall() : String{
    delay(3000L)
    return "This is netwok Call"
}
suspend fun doNetworkCall2() : String{
    delay(3000L)
    return "This is 2 netwok Call"
}
