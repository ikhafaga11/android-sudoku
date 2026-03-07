package com.example.sudoku_app.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sudoku_app.viewmodel.SudokuViewModel

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    sudokuViewModel: SudokuViewModel = viewModel()
) {

    val state by sudokuViewModel.uiState.collectAsState()
    val mistakeCount = state.mistakeCount
    val maxMistakeCount = state.maxMistakeCount


    FlowRow(
        modifier
            .fillMaxWidth()
            .padding(16.dp), horizontalArrangement = Arrangement.End
    ) {
        Text("Mistakes: $mistakeCount/$maxMistakeCount")
    }
}


@Preview()
@Composable
fun PreviewTopBar() {
    TopBar()
}
