package com.example.sudoku_app

import com.example.sudoku_app.domain.engine.SudokuGenerator
import org.junit.Assert.assertEquals
import org.junit.Test

class TestSudokuGenerator {

    @Test
    fun testAddToList() {
        val sudokuGenerator = SudokuGenerator()

        sudokuGenerator.generate()

        val expect = (listOf(1, 2, 3, 4, 5, 6, 7, 8))

        assertEquals(expect, sudokuGenerator.list)
    }

    @Test
    fun testGenerateRandomInt() {
        val sudokuGenerator = SudokuGenerator()
        sudokuGenerator.generate()
        val result = sudokuGenerator.list

        assertEquals(listOf(Pair(1, 2)), result)
    }
}