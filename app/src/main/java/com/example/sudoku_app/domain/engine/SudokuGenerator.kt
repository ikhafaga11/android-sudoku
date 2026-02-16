package com.example.sudoku_app.domain.engine

class SudokuGenerator {
    val rows: Array<BooleanArray> = Array(9) { BooleanArray(10) }
    val cols: Array<BooleanArray> = Array(9) { BooleanArray(10) }
    val grids: Array<BooleanArray> = Array(9) { BooleanArray(10) }
    val board: Array<IntArray> = Array(9) { IntArray(9) }

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

    fun mostConstrainedCell(): Triple<Int, Int, MutableList<Int>> {
        var smallestDomainSize: Int = Int.MAX_VALUE
        var bestDomain: MutableList<Int> = mutableListOf<Int>()
        var bestRow: Int = Int.MIN_VALUE
        var bestCol: Int = Int.MIN_VALUE

        for(r in 0..8) {
            for(c in 0..8) {
                if (board[r][c] == 0) {
                    val currentDomain = legalValues(r, c)


                    if (currentDomain.size < smallestDomainSize) {
                        smallestDomainSize = currentDomain.size
                        bestRow = r
                        bestCol = c
                        bestDomain = currentDomain
                    }
                }
            }
        }

        return Triple(bestRow, bestCol, bestDomain)
    }

    fun boardCompletionCheck(): Boolean{
        for(row in 0..8) {
            for(col in 0..8){
                if(board[row][col] == 0) return false
            }
        }

        return true
    }

    fun solve(): Boolean {
        if (boardCompletionCheck()){ return true }

        val (r, c, domain) = mostConstrainedCell()

        for(v in domain.shuffled()){
            board[r][c] = v
            rows[r][v] = true
            cols[c][v] = true
            grids[gridIndex(r, c)][v] = true

            if (solve()){
                return true}

            board[r][c] = 0
            rows[r][v] = false
            cols[c][v] = false
            grids[gridIndex(r, c)][v] = false
        }

        return false
    }

    fun generateSudokuBoard(): Array<IntArray> {
        solve()
        return board
    }
}