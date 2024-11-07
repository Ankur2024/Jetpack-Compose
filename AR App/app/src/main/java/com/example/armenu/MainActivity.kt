package com.example.armenu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.armenu.ui.theme.ARMenuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ARMenuTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ARMenuTheme {
        Greeting("Android")
    }
}

data class Food(
    var name: String,
    var imageId: Int
)

@Composable
fun Menu(modifier: Modifier = Modifier){
    val itemsList = listOf(
        Food("Burger", R.drawable.burger),
        Food("Instant", R.drawable.instant),
        Food("Momos", R.drawable.momos),
        Food("Pizza", R.drawable.pizza),
        Food("Ramen", R.drawable.ramen)
    )

    Row (
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ){
        IconButton(onClick = { /*TODO*/ }) {
            Icon(painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                contentDescription = "Previous"
            )



            IconButton(onClick = { /*TODO*/ }) {
                Icon(painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                    contentDescription = "Next"
                )
            }

        }
    }
}


@Composable
fun CircularImage(
    imageId: Int,
    modifier: Modifier = Modifier,

){
}