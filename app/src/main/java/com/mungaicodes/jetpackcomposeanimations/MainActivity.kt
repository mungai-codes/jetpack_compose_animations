package com.mungaicodes.jetpackcomposeanimations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mungaicodes.jetpackcomposeanimations.ui.theme.JetpackComposeAnimationsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeAnimationsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    AnimatedVisibility()
//                    AnimateAnyValue()
//                    AnimateMultipleValues()
//                    InfiniteAnimations()
//                    AnimatingContentChanges()
                }
            }
        }

    }
}

@Composable
fun AnimatedVisibility() {
    var isVisible by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Button(onClick = { isVisible = !isVisible }) {
            Text(text = "Toggle")
        }
        AnimatedVisibility(
            visible = isVisible,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            enter = slideInHorizontally() + fadeIn()
        ) {
            Box(modifier = Modifier.background(Color.Red)) {

            }
        }
    }
}

@Composable
fun AnimateAnyValue() {
    var isRound by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Button(onClick = { isRound = !isRound }) {
            Text(text = "Toggle")
        }
        val borderRadius by animateIntAsState(
            targetValue = if (isRound) 100 else 0,
            animationSpec = tween(100)
//            spring(
//                dampingRatio = Spring.DampingRatioHighBouncy,
//                stiffness = Spring.StiffnessVeryLow,
//            )
//            tween(
//                durationMillis = 3000,
//                delayMillis = 500
//            )
        )
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(borderRadius))
                .background(Color.Red)
        ) {

        }
    }
}

@Composable
fun AnimateMultipleValues() { //ie animating color and corner radius
    var isRound by remember {
        mutableStateOf(false)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Button(onClick = { isRound = !isRound }) {
            Text(text = "Toggle")
        }
        val transition = updateTransition(
            targetState = isRound,
            label = null
        )
        val borderRadius by transition.animateInt(
            transitionSpec = { tween(2000) },
            label = "borderRadius",
            targetValueByState = { isRound ->
                if (isRound) 100 else 0
            }
        )
        val color by transition.animateColor(
            transitionSpec = { tween(1000) },
            label = "color",
            targetValueByState = { isRound ->
                if (isRound) Color.Green else Color.Red
            }
        )
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(RoundedCornerShape(borderRadius))
                .background(color)
        )
    }

}

@Composable
fun InfiniteAnimations() {
    val transition = rememberInfiniteTransition()
    val color by transition.animateColor(
        initialValue = Color.Red,
        targetValue = Color.Green,
        animationSpec = infiniteRepeatable(
            animation = tween(2000),
            repeatMode = RepeatMode.Reverse
        )
    )
    Column(modifier = Modifier.fillMaxSize()) {
        Button(onClick = { }) {
            Text(text = "Toggle")
        }
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(color)
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatingContentChanges() {
    var isVisible by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Button(onClick = { isVisible = !isVisible }) {
            Text(text = "Toggle")
        }
        AnimatedContent(
            targetState = isVisible,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            content = { isVisible ->
                if (isVisible) {
                    Box(modifier = Modifier.background(Color.Green))
                } else {
                    Box(modifier = Modifier.background(Color.Red))
                }
            },
            transitionSpec = {
                slideInHorizontally(
                    initialOffsetX = {
                        //-it
                        if (isVisible) it else -it
                    }
                ) with slideOutHorizontally(
                    targetOffsetX = {
                        // it
                        if (isVisible) -it else it
                    }
                )
//                fadeIn() with fadeOut()
            }
        )
    }
}