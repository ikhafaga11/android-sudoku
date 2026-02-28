package com.example.sudoku_app.components

import android.R
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sudoku_app.viewmodel.SudokuViewModel

@Composable
fun Grid(modifier: Modifier = Modifier, sudokuViewModel: SudokuViewModel = viewModel()) {
    //PlaceHolder Board
    val state by sudokuViewModel.uiState.collectAsState()
    val selectedIndex = state.selectedIndex
    val onSelectedValue = state.selectedIndexValue
    val columnIndices = state.columnIndexList
    val rowIndices = state.rowIndexList
    val squareIndices = state.squareIndexList
    val board: List<Int> = state.puzzleBoard.flatMap { it.toList() }
    val notesBoard: List<MutableSet<Int>> = state.notesBoard.flatMap { it.toList() }

    Column(modifier = modifier.background(Color.White)) {
        Box(
            Modifier
                .aspectRatio(1f)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LazyVerticalGrid(
                modifier = modifier.matchParentSize(),
                verticalArrangement = Arrangement.Center,
                columns = GridCells.Fixed(9)
            ) {
                items(81) { index ->
                    val cell = board[index]
                    val notes = notesBoard[index]
                    val noteSize = notes.size
                    val isSelectedCell = index == selectedIndex
                    val isInColumn = index in columnIndices
                    val isInRow = index in rowIndices
                    val isInSquare = index in squareIndices
                    val lightBlue = 0xFF00000 // experiment
                    val lightYellow = 0xFFFFC107 // experiment
                    val darkBlue = 0xFF1E88E5 // experiment
                        Box(
                            modifier = modifier
                                .aspectRatio(1f)
                                .background(
                                    when {
                                        isSelectedCell -> Color(lightYellow)
                                        isInColumn -> Color(lightBlue)
                                        isInRow -> Color(lightBlue)
                                        isInSquare -> Color(lightBlue)
                                        else -> Color.Transparent

                                    }
                                )
                                .clickable {
                                    sudokuViewModel.onSelectedIndex(index, cell)
                                }
                                .padding(5.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = if(cell == 0) {
                                    notes.joinToString("")
                                } else {
                                    "$cell"
                                },
                                color = if(notes.isEmpty()){if(cell == onSelectedValue) Color(darkBlue) else Color.Black} else{
                                    Color.Red},
                                fontSize = if(notes.isEmpty()){if(cell == onSelectedValue) 25.sp else 20.sp} else {if (noteSize > 5) 5.sp else 10.sp},
                                fontWeight = if(cell==onSelectedValue) FontWeight.Bold else FontWeight.Normal,
                            )
                        }
                }
            }
            Canvas(
                modifier = modifier
                    .matchParentSize()
            ) {
                val cellSize = size.width / 9
                val canvasWidth = size.width
                val canvasHeight = size.height

                // draw vertical Lines
                for (i in 0..9) {
                    val x = i * cellSize
                    val strokeWidth: Float = if (i % 3 == 0) 4.dp.toPx() else 1.dp.toPx()


                    drawLine(
                        start = Offset(x = x, y = 0F),
                        end = Offset(x = x, y = canvasHeight),
                        color = Color.Black,
                        strokeWidth = strokeWidth

                    )
                }

                for (i in 0..9) {
                    val y = i * cellSize
                    val strokeWidth = if (i % 3 == 0) 4.dp.toPx() else 1.dp.toPx()


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
}

@Preview(showBackground = true)
@Composable
fun PreviewGrid() {
    Grid()
}