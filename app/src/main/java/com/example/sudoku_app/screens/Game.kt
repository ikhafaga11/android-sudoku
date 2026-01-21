package com.example.sudoku_app.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sudoku_app.components.Grid


@Composable
fun GameScreen(modifier: Modifier = Modifier) {
    Grid()
}

@Preview()
@Composable
fun PreviewGameScreen() {
    GameScreen()
}
