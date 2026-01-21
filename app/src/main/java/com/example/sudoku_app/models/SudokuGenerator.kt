package com.example.sudoku_app.models

object SudokuGenerator {
    fun fillBoard(board: SudokuBoard): Boolean {
        for (row in 0 until 9) {
            for (col in 0 until 9) {
                if (board.cells[row][col].value == null) {
                    val numbers = (1..9).shuffled()
                    for (num in numbers) {
                        if (board.isMoveValid(row, col, num)) {
                            board.cells[row][col].value = num
                            if (fillBoard(board)) return true
                            board.cells[row][col].value = null
                        }
                    }
                    return false
                }
            }
        }
        return true
    }
    fun nakedSingleStep(board: SudokuBoard): Boolean {
        for (row in 0 until 9) {
            for (col in 0 until 9) {
                val cell = board.cells[row][col]
                if (cell.value == null) {
                    val possible = (1..9).filter { board.isMoveValid(row, col, it) }
                    if (possible.size == 1) {
                        cell.value = possible[0]
                        return true
                    }
                }
            }
        }
        return false
    }

    fun canSolveWithNakedSingles(board: SudokuBoard): Boolean {
        val temp = board.copy()
        while (true) {
            val progress = nakedSingleStep(temp)
            if (!progress) break
        }
        return temp.isComplete()
    }

    fun makePuzzle(fullBoard: SudokuBoard, clues: Int = 30): Pair<SudokuBoard, String> {
        val puzzle = fullBoard.copy()
        val cells = mutableListOf<Pair<Int, Int>>()
        for (r in 0 until 9) for (c in 0 until 9) cells.add(r to c)
        cells.shuffle()
        val removals = 81 - clues
        for (i in 0 until removals) {
            val (r, c) = cells[i]
            puzzle.cells[r][c].value = null
        }

        for (r in 0 until 9) for (c in 0 until 9) {
            puzzle.cells[r][c].isFixed = puzzle.cells[r][c].value != null
        }

        val difficulty = if (canSolveWithNakedSingles(puzzle)) "Easy" else "Harder"
        return puzzle to difficulty
    }
}