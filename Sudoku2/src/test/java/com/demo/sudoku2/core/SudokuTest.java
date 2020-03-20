package com.demo.sudoku2.core;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SudokuTest {

    private Sudoku sudoku;

    @BeforeEach
    void init() {
        Integer[] puzzle = {
            5, 3, null, null, 7, null, null, null, null,
            6, null, null, 1, 9, 5, null, null, null,
            null, 9, 8, null, null, null, null, 6, null,
            8, null, null, null, 6, null, null, null, 3,
            4, null, null, 8, null, 3, null, null, 1,
            7, null, null, null, 2, null, null, null, 6,
            null, 6, null, null, null, null, 2, 8, null,
            null, null, null, 4, 1, 9, null, null, 5,
            null, null, null, null, 8, null, null, 7, 9};
        sudoku = new Sudoku(puzzle);
    }

    @Test
    void testConsturctor() {
        System.out.println("testConsturctor");
        System.out.println(sudoku);
        for (int i = 0; i < sudoku.getPuzzle().length; i++) {
            assertEquals(sudoku.getPuzzle()[i],
                    sudoku.getSolution().get(i).result);
            if (sudoku.getPuzzle()[i] == null) {
                assertTrue(sudoku.getSolution().get(i).suggestions.size() == 9);
            }
        }
    }

    @Test
    void testCalculate() {
        sudoku.calculate();
        System.out.println("testCalculate");
        System.out.println(sudoku);
        Integer[] expectedSolution = {
            5, 3, 4, 6, 7, 8, 9, 1, 2,
            6, 7, 2, 1, 9, 5, 3, 4, 8,
            1, 9, 8, 3, 4, 2, 5, 6, 7,
            8, 5, 9, 7, 6, 1, 4, 2, 3,
            4, 2, 6, 8, 5, 3, 7, 9, 1,
            7, 1, 3, 9, 2, 4, 8, 5, 6,
            9, 6, 1, 5, 3, 7, 2, 8, 4,
            2, 8, 7, 4, 1, 9, 6, 3, 5,
            3, 4, 5, 2, 8, 6, 1, 7, 9};
        for (int i = 0; i < sudoku.getPuzzle().length; i++) {
            assertEquals(expectedSolution[i],
                    sudoku.getSolution().get(i).result);
            assertTrue(sudoku.getSolution().get(i).suggestions.size() == 1
                    && sudoku.getSolution().get(i).suggestions.contains(expectedSolution[i]));
        }
    }

    @AfterEach
    void tearDown() {
        sudoku = null;
    }
}
