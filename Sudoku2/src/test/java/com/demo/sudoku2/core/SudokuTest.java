package com.demo.sudoku2.core;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SudokuTest {

    private static Integer[] validPuzzle;
    private static Integer[] expectedSolution;
    private static Integer[] puzzleWithWrongLength;
    private static Integer[] puzzleWithDuplicate;

    @BeforeAll
    static void init() {
        validPuzzle = new Integer[]{
            5, 3, null, null, 7, null, null, null, null,
            6, null, null, 1, 9, 5, null, null, null,
            null, 9, 8, null, null, null, null, 6, null,
            8, null, null, null, 6, null, null, null, 3,
            4, null, null, 8, null, 3, null, null, 1,
            7, null, null, null, 2, null, null, null, 6,
            null, 6, null, null, null, null, 2, 8, null,
            null, null, null, 4, 1, 9, null, null, 5,
            null, null, null, null, 8, null, null, 7, 9};
        expectedSolution = new Integer[]{
            5, 3, 4, 6, 7, 8, 9, 1, 2,
            6, 7, 2, 1, 9, 5, 3, 4, 8,
            1, 9, 8, 3, 4, 2, 5, 6, 7,
            8, 5, 9, 7, 6, 1, 4, 2, 3,
            4, 2, 6, 8, 5, 3, 7, 9, 1,
            7, 1, 3, 9, 2, 4, 8, 5, 6,
            9, 6, 1, 5, 3, 7, 2, 8, 4,
            2, 8, 7, 4, 1, 9, 6, 3, 5,
            3, 4, 5, 2, 8, 6, 1, 7, 9};
        puzzleWithWrongLength = new Integer[]{
            5, 3, null, null, 7, null, null, null, null};
        puzzleWithDuplicate = new Integer[]{
            5, 3, 3, null, 7, null, null, null, null,
            6, null, null, 1, 9, 5, null, null, null,
            null, 9, 8, null, null, null, null, 6, null,
            8, null, null, null, 6, null, null, null, 3,
            4, null, null, 8, null, 3, null, null, 1,
            7, null, null, null, 2, null, null, null, 6,
            null, 6, null, null, null, null, 2, 8, null,
            null, null, null, 4, 1, 9, null, null, 5,
            null, null, null, null, 8, null, null, 7, 9};
    }

    @Test
    void testConsturctorWithValidPuzzle() {
        Sudoku sudoku = new Sudoku(validPuzzle);
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
    void testConsturctorWithInvalidPuzzleLength() {
        IllegalArgumentException exception
                = assertThrows(IllegalArgumentException.class, () -> {
                    Sudoku sudoku = new Sudoku(puzzleWithWrongLength);
                });
        String expectedMessage = "given puzzle is invalid";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testConsturctorWithInvalidPuzzleDuplicate() {
        IllegalArgumentException exception
                = assertThrows(IllegalArgumentException.class, () -> {
                    Sudoku sudoku = new Sudoku(puzzleWithDuplicate);
                });
        String expectedMessage = "given puzzle is invalid";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testCalculate() {
        Sudoku sudoku = new Sudoku(validPuzzle);
        sudoku.calculate();
        System.out.println("testCalculate");
        System.out.println(sudoku);
        for (int i = 0; i < sudoku.getPuzzle().length; i++) {
            assertEquals(expectedSolution[i],
                    sudoku.getSolution().get(i).result);
            assertTrue(sudoku.getSolution().get(i).suggestions.size() == 1
                    && sudoku.getSolution().get(i).suggestions
                            .contains(expectedSolution[i]));
        }
    }

    @Test
    void testUnsolvedPuzzle() {
        Sudoku sudoku = new Sudoku(validPuzzle);
        Assertions.assertFalse(sudoku.isSolved());
    }

    @Test
    void testSolvedPuzzle() {
        Sudoku sudoku = new Sudoku(expectedSolution);
        assertTrue(sudoku.isSolved());
    }

    @Test
    void testEquality() {
        Sudoku unsolvedSudoku1 = new Sudoku(validPuzzle);
        Sudoku unsolvedSudoku2 = new Sudoku(validPuzzle);
        assertEquals(unsolvedSudoku1, unsolvedSudoku2);
        Sudoku solvedSudoku1 = new Sudoku(expectedSolution);
        Sudoku solvedSudoku2 = new Sudoku(expectedSolution);
        assertEquals(solvedSudoku1, solvedSudoku2);
    }

    @AfterAll
    static void tearDown() {
        validPuzzle = null;
        expectedSolution = null;
        puzzleWithWrongLength = null;
        puzzleWithDuplicate = null;
    }

}
