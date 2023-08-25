package com.example.composepractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.composepractice.ui.theme.ComposePracticeTheme
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePracticeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    //modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    ImageCard(
//                        title = "Title",
//                        painter = painterResource(id = R.drawable._249545),
//                        contentDesc = "Content Desc"
//                    )
                    //StyledText()
                    //ColorStateExample(Modifier.fillMaxSize())
                    //SnackBarExample(Modifier.fillMaxSize())
                    //ListExample(Modifier.fillMaxSize())
                    //AnimationExample()
                    CircularProgressBarExample()
                }
            }
        }
    }
}

@Composable
fun CircularProgressBarExample(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        CircularProgressBar(
            percentage = 0.8f,
            number = 100,
            animDelay = 2000
        )
    }
}


@Composable
fun CircularProgressBar(
    percentage: Float,
    number: Int,
    fontSize: TextUnit = 28.sp,
    radius: Dp = 50.dp,
    color: Color = Color.Green,
    strokeWidth: Dp = 8.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0,
    modifier: Modifier = Modifier
) {
    var animationPlayed by remember { mutableStateOf(false) }
    val currentPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(durationMillis = animDuration, delayMillis = animDelay), label = ""
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        modifier = modifier.size(radius * 2f),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(radius * 2f)) {
            drawArc(
                color = color,
                startAngle = -90f,
                sweepAngle = 360 * currentPercentage.value,
                useCenter = false,
                style = Stroke(
                    strokeWidth.toPx(),
                    cap = StrokeCap.Round
                )
            )
        }

        Text(
            text = (currentPercentage.value * number).toInt().toString(),
            color = Color.Black,
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun AnimationExample(modifier: Modifier = Modifier) {
    var sizeState by remember {
        mutableStateOf(200.dp)
    }

    val size by animateDpAsState(
        targetValue = sizeState,
        label = "",
        animationSpec = spring(Spring.DampingRatioMediumBouncy)
    )

    Box(
        modifier = modifier
            .size(size)
            .background(Color.Red),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            sizeState += 50.dp
        }) {
            Text(text = "increase size")
        }
    }
}

@Composable
fun ListExample(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(5000) {
            Text(
                text = "Item $it",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 18.dp)
            )
        }
    }
}

@Composable
fun SnackBarExample(modifier: Modifier = Modifier) {
    val snackbarHostState = remember { SnackbarHostState() }
    var userText by remember { mutableStateOf("") }
    var scope = rememberCoroutineScope()

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = userText,
                label = { Text(text = "Enter Your Name") },
                onValueChange = { newString ->
                    userText = newString
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                scope.launch {
                    snackbarHostState.showSnackbar("Hello $userText!")
                }
            }) {
                Text(text = "Greet Me!")
            }
        }

    }
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .background(Color.Green)
            .fillMaxHeight(0.5f)
            .fillMaxWidth()
            .border(5.dp, Color.Magenta)
            .padding(5.dp)
            .border(5.dp, Color.Blue)
            .padding(5.dp)
            .border(5.dp, Color.Red)
            .padding(5.dp)
    ) {
        Text(text = "Hello")
        Text(text = "World")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposePracticeTheme {
        ImageCard(
            title = "Title",
            painter = painterResource(id = R.drawable._249545),
            contentDesc = "Content Desc"
        )
    }
}

@Composable
fun ImageCard(
    title: String,
    painter: Painter,
    contentDesc: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Box(modifier = Modifier.height(200.dp)) {
            Image(
                painter = painter,
                contentDescription = contentDesc,
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 300f
                        )
                    )
            ) {

            }
            Text(
                text = title,
                style = TextStyle(color = Color.White, fontSize = 16.sp),
                modifier = Modifier
                    .padding(12.dp)
                    .align(Alignment.BottomStart)
            )

        }
    }
}

@Composable
fun StyledText(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF101010))
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Green,
                        fontSize = 50.sp
                    )
                ) {
                    append("H")
                }
                append("ello")
                withStyle(
                    style = SpanStyle(
                        color = Color.Green,
                        fontSize = 50.sp
                    )
                ) {
                    append("C")
                }
                append("ompose")
            },
            color = Color.White,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            modifier = modifier.fillMaxWidth()
        )
    }
}

@Composable
fun ColorStateExample(modifier: Modifier = Modifier) {
    var color by remember {
        mutableStateOf(Color.Yellow)
    }
    Column(modifier) {
        ColorBox(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ) {
            color = it
        }
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
                .background(color)
        ) {

        }
    }
}

@Composable
fun ColorBox(
    modifier: Modifier = Modifier,
    updateColor: (Color) -> Unit
) {
    Box(
        modifier = modifier
            .background(Color.Red)
            .clickable {
                updateColor(
                    Color(
                        Random.nextFloat(),
                        Random.nextFloat(),
                        Random.nextFloat(),
                        1f
                    )
                )
            }
    ) {

    }
}