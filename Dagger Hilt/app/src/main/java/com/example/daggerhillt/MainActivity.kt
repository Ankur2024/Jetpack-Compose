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
import com.example.daggerhillt.manualdependecy.BaseApp
import com.example.daggerhillt.manualdependecy.di.DownloadFactory
import com.example.daggerhillt.manualdependecy.di.Downloader
import com.example.daggerhillt.ui.theme.DaggerHilltTheme

class MainActivity : ComponentActivity() {
    private lateinit var baseApp: BaseApp
    private val download = DownloadFactory.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DaggerHilltTheme {
                baseApp = BaseApp()
                baseApp.car.getCar()
                download.getDownload()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
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