package com.example.sudoku_app.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sudoku_app.domain.engine.SudokuGenerator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class GameUIState(
    val selectedIndex: Int? = null,
    val selectedIndexValue: Int? = null,
    val columnIndexList: List<Int> = emptyList(),
    val squareIndexList: List<Int> = emptyList(),
    val rowIndexList: List<Int> = emptyList(),
    val isNoteMode: Boolean = false,
    val puzzleBoard: Array<IntArray> = Array(9){ IntArray(9) },
    val solutionBoard: Array<IntArray> = Array(9){IntArray(9)},
    val mistakesBoard: Array<IntArray> = Array(9){IntArray(9)},
    val notesBoard: Array<Array<MutableSet<Int>>> = Array(9) { Array(9) { mutableSetOf<Int>() }},
    val mistakeCount: Int = 0,
    val maxMistakeCount: Int = 3,
)

class SudokuViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUIState())
    private val generator = SudokuGenerator()
    val uiState: StateFlow<GameUIState> = _uiState.asStateFlow()

    fun noteMode() {
        val toggle = if (!_uiState.value.isNoteMode) true else false
        _uiState.value = _uiState.value.copy(
            isNoteMode = toggle
        )
    }

    fun highlightColumn(index: Int) {
        val remainder = index % 9

        val columnIndices = (0 until 9).map { i -> (9 * i) + remainder }

        _uiState.value = _uiState.value.copy(
            columnIndexList = columnIndices
        )
    }

    fun highlightSquare(index: Int) {
        val xSquareIndex = index % 3
        val y = index / 9
        val ySquareIndex = y % 3

        val xPreviousOne =
            (0 until 3).map { i -> (index - xSquareIndex) - (9 * ySquareIndex) + i }
        val xPreviousTwo = xPreviousOne.map { value -> (value + 9) }
        val xPreviousThree = xPreviousTwo.map { value -> (value + 9) }

        val results = xPreviousOne + xPreviousTwo + xPreviousThree

        _uiState.value = _uiState.value.copy(
            squareIndexList = results,
        )
    }

    fun highlightRow(index: Int) {
        val remainder = index % 9
        val rowStart = index - (remainder)

        val rowIndices = (0 until 9).map { i -> rowStart + i }

        _uiState.value = _uiState.value.copy(
            rowIndexList = rowIndices
        )
    }

    fun onSelectedIndex(index: Int, cell: Int) {
        if (_uiState.value.selectedIndex == null) {
            _uiState.value = _uiState.value.copy(
                selectedIndex = index,
                selectedIndexValue = cell
            )
            highlightColumn(index)
            highlightRow(index)
            highlightSquare(index)
        } else if (_uiState.value.selectedIndex != index) {

            _uiState.value = _uiState.value.copy(
                selectedIndex = index,
                selectedIndexValue = cell

            )
            highlightColumn(index)
            highlightRow(index)
            highlightSquare(index)
        } else {
            _uiState.value = _uiState.value.copy(
                selectedIndex = null,
                selectedIndexValue = null,
                columnIndexList = emptyList(),
                rowIndexList = emptyList(),
                squareIndexList = emptyList()

            )
        }
    }

    fun inputNote(i: Int?, v: Int?){
        if(!_uiState.value.isNoteMode) return
        i ?: return
        v ?:  return
        val r = i / 9
        val c = i % 9
        if(_uiState.value.puzzleBoard[r][c] != 0) return

        val newNotesBoard = Array(9){ _uiState.value.notesBoard[it].clone() }
        val newMistakeBoard = Array(9){_uiState.value.mistakesBoard[it].clone()}
        val cell: MutableSet<Int> = newNotesBoard[r][c]

        if(cell.contains(v)) cell.remove(v) else cell.add(v)
        newMistakeBoard[r][c] = 0


        _uiState.value = _uiState.value.copy(
            notesBoard = newNotesBoard,
            mistakesBoard =  newMistakeBoard
        )
    }

    fun inputNumber(i: Int?, v: Int?) {
        if(_uiState.value.mistakeCount == _uiState.value.maxMistakeCount) return
        if(_uiState.value.isNoteMode) return
        i ?: return
        v ?: return

        val r = i / 9
        val c = i % 9
        if( _uiState.value.puzzleBoard[r][c] != 0) return

        var newMistakeCount = _uiState.value.mistakeCount
        val newBoard = Array(9){_uiState.value.puzzleBoard[it].clone()}
        val newNoteBoard = Array(9){_uiState.value.notesBoard[it].clone()}
        val newMistakeBoard = Array(9){_uiState.value.mistakesBoard[it].clone()}

        if( _uiState.value.solutionBoard[r][c] != v && newMistakeCount < _uiState.value.maxMistakeCount) {
            newMistakeBoard[r][c] = v
            newNoteBoard[r][c].removeAll(1..9)
            newMistakeCount += 1
        } else {
            newBoard[r][c] = v
            newNoteBoard[r][c].removeAll(1..9)
            newMistakeBoard[r][c] = 0
        }

        _uiState.value = _uiState.value.copy(
            puzzleBoard = newBoard,
            notesBoard = newNoteBoard,
            mistakesBoard = newMistakeBoard,
            mistakeCount = newMistakeCount
        )
    }


    fun generateBoard( ){
        val (sb, pb) = generator.generateBoards()
        _uiState.value = _uiState.value.copy(
            solutionBoard = sb,
            puzzleBoard = pb,
        )
    }
}