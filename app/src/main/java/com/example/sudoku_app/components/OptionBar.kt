package com.example.sudoku_app.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sudoku_app.R
import com.example.sudoku_app.viewmodel.SudokuViewModel

@Composable
fun OptionBar(modifier: Modifier = Modifier, sudokuViewModel: SudokuViewModel) {
    FlowRow(
//        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        itemVerticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {sudokuViewModel.noteMode()}) {
            Icon(
                painter = painterResource(R.drawable.pencil),
                contentDescription = "Pencil",
                modifier = modifier.padding(2.dp)

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

