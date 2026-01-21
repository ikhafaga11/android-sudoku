package com.example.sudoku_app.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.sudoku_app.R


@Composable
fun HomeScreen(modifier: Modifier = Modifier, onGameStart: () -> Unit) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row() {
            Text(stringResource(R.string.homepage_heading))
        }
        Spacer(modifier = modifier.height(16.dp))
        Row() {
            Button(onClick = onGameStart) {
                Text(stringResource(R.string.start_game_btn))
            }
        }
    }
}
