package com.example.sudoku_app.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sudoku_app.R
import com.example.sudoku_app.viewmodel.SudokuViewModel

@Composable
fun OptionBar(modifier: Modifier = Modifier,
              sudokuViewModel: SudokuViewModel = viewModel()) {
    val state by sudokuViewModel.uiState.collectAsState()
    val isNoteMode = state.isNoteMode
    val lightYellow = 0xFFFFC107 // experiment

    FlowRow(
        horizontalArrangement = Arrangement.End,
        itemVerticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {sudokuViewModel.noteMode()}) {
            Icon(
                painter = painterResource(R.drawable.pencil),
                contentDescription = "Pencil",
                modifier = modifier.padding(2.dp),
                tint = if(isNoteMode) Color(lightYellow) else Color.Black
            )
        }


    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewPencil() {
//    OptionBar()
//}
//

