package com.jaishreeram

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.jaishreeram.ui.theme.JaiShreeRamTheme
import kotlinx.coroutines.delay
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JaiShreeRamTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    JaiShreeRamScreen()
                }
            }
        }
    }
}

@Composable
fun JaiShreeRamScreen() {
    var secondState by remember { mutableStateOf(false) }
    val snowflakes = remember { List(100) { generateRandomSnowflake() } }
    val infiniteTransition = rememberInfiniteTransition(label = "")

    val offsetY by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )
    Canvas(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFFFC107))) {
        snowflakes.forEach { snowflake ->
            drawSnowflake(snowflake, offsetY % size.height)
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.image2), contentDescription = ""
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TypingText(listOf("ज", "य", " ", "श्री", " ", "रा", "म")) { secondState = true }
                    if (secondState) {
                        TypingText("Ayodhya Ram Mandir".map { it.toString() }) {}
                    }
                }
            }
        }
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = R.drawable.img), contentDescription = ""
        )
    }
}

@Composable
fun TypingText(text: List<String>, stateChanged: () -> Unit) {
    var typedText by remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        text.forEachIndexed { index, _ ->
            delay(300)
            typedText += text[index]
        }
        stateChanged()
    }
    Text(
        text = typedText,
        style = TextStyle(
            fontSize = 60.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            textAlign = TextAlign.Center
        ),
        color = Color(0xFFFA0318),
        modifier = Modifier
    )
}

fun generateRandomSnowflake(): Snowflake {
    return Snowflake(
        x = Random.nextFloat(),
        y = Random.nextFloat() * 500f,
        radius = Random.nextFloat() * 3f + 3f,
        speed = Random.nextFloat() * 1.2f + 1f
    )
}

data class Snowflake(
    var x: Float,
    var y: Float,
    var radius: Float,
    var speed: Float,
)

fun DrawScope.drawSnowflake(snowflake: Snowflake, offsetY: Float) {
    val newY = (snowflake.y + offsetY * snowflake.speed) % size.height
    drawCircle(
        Color.Black,
        radius = snowflake.radius,
        center = Offset(snowflake.x * size.width, newY)
    )
}

