package com.example.sudoku_app.domain.engine

class SudokuGenerator {
    val rows = Array(9) { BooleanArray(10) }
    val cols = Array(9) { BooleanArray(10) }
    val grids = Array(9) { BooleanArray(10) }
    val board = Array(9) {IntArray(9)}
    var isBoardComplete = false
    var count = 0

    fun gridIndex (r: Int,c: Int): Int {
        return (r/3) * 3 + (c/3)
    }

    fun legalValues(r: Int, c: Int): MutableList<Int>{
        val legalValues = mutableListOf<Int>()

        for(i in 1..9){
            if(rows[r][i]
                || cols[c][i]
                || grids[gridIndex(r, c)][i]) continue else legalValues.add(i)
        }

        return legalValues
    }

    fun mostConstrainedCell(): Pair<Int, Int>{
        var bestCell: Pair<Int, Int> = Pair((0..8).random(),(0..8).random())
        var smallestDomainSize: Int = 8
        var domain = mutableListOf<Int>()

        for(r in 0..8){
            for(c in 0..8){
                if (board[r][c] == 0) {
                    domain = legalValues(r, c);

                    if (domain.size < smallestDomainSize){
                        smallestDomainSize = domain.size
                        bestCell = Pair(r, c)
                    }
                }
            }
        }
        return bestCell
    }

    fun generate() {

        while(!isBoardComplete) {
            val (r, c) = mostConstrainedCell()
            val v = legalValues(r, c).random()

            if (board[r][c] != 0) continue
            if (cols[c][v]) continue
            if (rows[r][v]) continue
            if (grids[gridIndex(r, c)][v]) continue

            board[r][c] = v
            rows[r][v] = true
            cols[c][v] = true
            grids[gridIndex(r, c)][v] = true
            count++

            if(count == 81) isBoardComplete = true

        }
    }

}