package com.coding.meet.happynewyear

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.coding.meet.happynewyear.ui.theme.HappyNewYearTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HappyNewYearTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    NewYearScreen()
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HappyNewYearTheme {
        NewYearScreen()
    }
}

@Composable
fun NewYearScreen() {
    var secondState by remember { mutableStateOf(false) }
    var thirdState by remember { mutableStateOf(false) }
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.firework_animation)
    )

    LottieAnimation(
        modifier = Modifier.fillMaxSize(),
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TypingText("Happy") { secondState = true }
        if (secondState) {
            TypingText("New") { thirdState = true }
        }
        if (thirdState) {
            TypingText("Year") {}
        }
    }
}

@Composable
fun TypingText(text: String, stateChanged: () -> Unit) {
    var typedText by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        text.forEachIndexed { index, _ ->
            delay(500)
            typedText = text.substring(0, index + 1)
        }
        stateChanged()
    }
    Text(
        text = typedText,
        style = TextStyle(
            fontSize = 72.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Cursive
        ),
        color = Color(0xFF03FA6E),
        modifier = Modifier
    )
}

fun main(){
    println("🎉 Happy New Year! 🎉")
}