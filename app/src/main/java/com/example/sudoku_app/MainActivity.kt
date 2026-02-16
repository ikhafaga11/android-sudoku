package com.example.sudoku_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sudoku_app.screens.GameScreen
import com.example.sudoku_app.screens.HomeScreen
import com.example.sudoku_app.ui.theme.SudokuappTheme
import com.example.sudoku_app.viewmodel.SudokuViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SudokuappTheme {
                App()
            }
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    val sudokuViewModel: SudokuViewModel = viewModel()
    val state by sudokuViewModel.uiState.collectAsState()
    NavHost(navController = navController, startDestination = "home") {

        composable(route = "home") {
            HomeScreen(onGameStart = {
                navController.navigate("game")
            })
        }
        composable(route = "game") {
            GameScreen(sudokuViewModel = sudokuViewModel)
            sudokuViewModel.generateBoard()
        }
    }
}



