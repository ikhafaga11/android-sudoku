package com.example.sudoku_app.domain.engine

class SudokuGenerator {
    //structure [[false * 10] * 9] requires (int, int) to access values
    val rows = Array(9) { BooleanArray(10) }
    val cols = Array(9) { BooleanArray(10) }
    val grids = Array(9) { BooleanArray(10) }
    val board = Array(9) { IntArray(9) }
    var isBoardComplete = false
    var count = 0

    fun gridIndex(r: Int, c: Int): Int {
        return (r / 3) * 3 + (c / 3)
    }

    fun generate() {

        while (!isBoardComplete) {
            val v = (1..9).random()
            val c = (0..8).random()
            val r = (0..8).random()
            val rIndex = rows.mapIndexed{i, c ->
                i to c.count{ it }
            }
            val rIndexConstrained = rIndex.maxBy{it.second}
            val cIndex = cols.mapIndexed{i, c ->
                i to c.count{ it }
            }
            val cIndexConstrained = cIndex.maxBy{it.second}

            if (board[r][c] == 0) continue
            if (cols[c][v]) continue
            if (rows[r][v]) continue
            if (grids[gridIndex(r, c)][v]) continue

            board[r][c] = v
            rows[r][v] = true
            cols[c][v] = true
            grids[gridIndex(r, c)][v] = true
            count++


            if (count == 81) isBoardComplete = true

        }
    }

}