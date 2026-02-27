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
        var smallest: Int = Int.MAX_VALUE
        val candidates: MutableList<Triple<Int, Int, MutableList<Int>>> = mutableListOf()

        for(r in 0..8) {
            for(c in 0..8) {
                if (board[r][c] == 0) {
                    val domain = legalValues(r, c)

                    when {
                        domain.size < smallest -> {
                            smallest = domain.size
                            candidates.clear()
                            candidates.add(Triple(r, c, domain))
                        }
                        domain.size == smallest -> {
                            candidates.add(Triple(r,c,domain))
                        }
                    }
                }
            }
        }

        return candidates.random()
    }

    fun boardCompletionCheck(): Boolean{
        for(row in 0..8) {
            for(col in 0..8){
                if(board[row][col] == 0) return false
            }
        }

        return true
    }

    fun generateSolution(): Boolean {
        if (boardCompletionCheck()){ return true }

        val (r, c, domain) = mostConstrainedCell()

        for(v in domain.shuffled()){
            board[r][c] = v
            rows[r][v] = true
            cols[c][v] = true
            grids[gridIndex(r, c)][v] = true

            if (generateSolution()){
                return true}
            board[r][c] = 0
            rows[r][v] = false
            cols[c][v] = false
            grids[gridIndex(r, c)][v] = false
        }

        return false
    }

    fun forcedCells(): MutableList<Triple<Int,Int, Int>>{
        var changed: Boolean
        val forced = mutableListOf<Triple<Int,Int,Int>>()

        do{
            changed = false
            for(r in 0..8) for(c in 0..8) if(board[r][c] == 0) {
                val domain = legalValues(r,c)
                if(domain.size == 1) {
                    val (v) = domain
                    board[r][c] = v
                    rows[r][v] = true
                    cols[c][v] = true
                    grids[gridIndex(r,c)][v] = true
                    forced.add(Triple(r, c,v))
                    changed = true
                }
            }

        } while(changed)

        undoForcedCells(forced)

        return forced
    }

    fun undoForcedCells(forced: MutableList<Triple<Int, Int, Int>>){
        for((r,c,v) in forced){
            board[r][c] = 0
            rows[r][v] = false
            cols[c][v] = false
            grids[gridIndex(r,c)][v] = false
        }
    }
    fun generatePuzzle (count: Int=0, difficulty: Int=30): Boolean {
        if(count == difficulty) return true

        val candidates = mutableListOf<Pair<Int,Int>>()
        for(r in 0..8) for(c in 0..8) if(board[r][c] != 0) candidates.add(r to c)
        if(candidates.isEmpty()) return true
        val (r, c) = candidates.random()
        val v = board[r][c]

        board[r][c] = 0
        rows[r][v] = false
        cols[c][v] = false
        grids[gridIndex(r,c)][v] = false
        val forced = forcedCells()

        if(generatePuzzle(count + 1, difficulty)) return true

        board[r][c] = v
        rows[r][v] = true
        cols[c][v] = true
        grids[gridIndex(r,c)][v] = true
        undoForcedCells(forced)


        return false
    }

    fun generateBoards(): Pair<Array<IntArray>,Array<IntArray>>{

        generateSolution()

        val solutionBoard = Array(9) {board[it].clone()}

        generatePuzzle()

        val puzzleBoard = Array(9) {board[it].clone()}

        return (solutionBoard to puzzleBoard)
    }
}