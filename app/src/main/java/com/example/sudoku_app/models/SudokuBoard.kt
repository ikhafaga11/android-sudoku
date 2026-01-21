package com.example.sudoku_app.models
class SudokuBoard(val size: Int = 9) {
    val cells: List<List<SudokuCell>> = List(size) { row ->
        List(size) { col -> SudokuCell(row, col) }
    }
    fun isMoveValid(row: Int, col: Int, number: Int): Boolean {
        if (cells[row].any { it.value == number }) return false
        if (cells.any { it[col].value == number }) return false
        val boxRow = (row / 3) * 3
        val boxCol = (col / 3) * 3
        for (r in boxRow until boxRow + 3) {
            for (c in boxCol until boxCol + 3) {
                if (cells[r][c].value == number) return false
            }
        }
        return true
    }

    fun isComplete(): Boolean {
        return cells.all { row -> row.all { it.value != null } }
    }

    fun copy(): SudokuBoard {
        val newBoard = SudokuBoard(size)
        for (r in 0 until size) {
            for (c in 0 until size) {
                newBoard.cells[r][c].value = this.cells[r][c].value
                newBoard.cells[r][c].isFixed = this.cells[r][c].isFixed
            }
        }
        return newBoard
    }
}