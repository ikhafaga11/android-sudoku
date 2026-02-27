package com.example.sudoku_app.screens

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sudoku_app.components.Grid
import com.example.sudoku_app.components.NumberPad
import com.example.sudoku_app.viewmodel.SudokuViewModel


@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    sudokuViewModel: SudokuViewModel = viewModel()
) {
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    if(isLandscape){
        Row(modifier.systemBarsPadding().displayCutoutPadding().fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Row(modifier.padding(10.dp).weight(4F), horizontalArrangement = Arrangement.Center) {
                Grid(sudokuViewModel = sudokuViewModel)
            }
            VerticalDivider(thickness = 2.dp)
            Row(modifier.width(150.dp).padding(0.dp).fillMaxHeight().weight(1F), verticalAlignment = Alignment.CenterVertically) {
                NumberPad(sudokuViewModel = sudokuViewModel)
            }
        }
    } else {
    Column(
        modifier = modifier
            .fillMaxSize()
            .systemBarsPadding(),
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp)
                .weight(2f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Grid(sudokuViewModel = sudokuViewModel)
        }

        HorizontalDivider(thickness = 2.dp)

        Row(modifier = modifier
            .weight(1.5f)
            .padding(top = 16.dp)) {
            NumberPad(sudokuViewModel = sudokuViewModel)
        }
    }}
}

@Preview(showBackground = true)
@Composable
fun PreviewGameScreen() {
    GameScreen()
}
