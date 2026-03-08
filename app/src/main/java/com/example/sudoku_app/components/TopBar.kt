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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

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
            .padding(10.dp), horizontalArrangement = Arrangement.Start
    ) {
        val text = "Mistakes: $mistakeCount / $maxMistakeCount"
        Text(buildAnnotatedString {
            text.split(" ").forEachIndexed{index, word ->
                if(word == "$mistakeCount" && mistakeCount > 0){
                    withStyle(SpanStyle(color = Color.Red, fontWeight = FontWeight.Bold, fontSize = 20.sp)) {append(word)}
                } else {
                    append(word)
                }
                if(index != text.split(" ").lastIndex) append(" ")
             }
        })
    }
}


@Preview()
@Composable
fun PreviewTopBar() {
    TopBar()
}
