package com.example.sudoku_app.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun GameScreen(modifier: Modifier = Modifier) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Canvas(modifier = modifier.size(360.dp)) {
            val cellSize = size.width / 9
            val canvasWidth = size.width
            val canvasHeight = size.height

            // draw vertical Lines
            for (i in 1..9) {
                val x = i * cellSize
                val strokeWidth: Float = if(i % 3 == 0 && i != 9) 4.dp.toPx() else 1.dp.toPx()


                drawLine(
                    start = Offset(x = x, y = 0F),
                    end = Offset(x = x, y = canvasHeight),
                    color = Color.Black,
                    strokeWidth = strokeWidth

                )
            }

            for (i in 1..9) {
                val y = i * cellSize
                val strokeWidth = if(i % 3 == 0 && i != 9) 4.dp.toPx() else 1.dp.toPx()


                drawLine(
                    start = Offset(x = canvasWidth, y = y),
                    end = Offset(x = 0F, y = y),
                    color = Color.Black,
                    strokeWidth = strokeWidth

                )
            }

        }
    }
}

@Preview()
@Composable
fun PreviewGameScreen() {
    GameScreen()
}
