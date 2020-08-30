package com.demo.sudoku2.core;

import java.util.Random;
import java.util.Set;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CellTest {

    private static Integer validNum;
    private static Integer invalidNum;

    @BeforeAll
    static void init() {
        // Obtain a random number in range [1, 9].
        validNum = new Random().nextInt(9) + 1;
        // Obtain a random number outside range [1, 9].
        do {
            invalidNum = new Random().nextInt();
        } while (invalidNum >= 1 && invalidNum <= 9);
    }

    @Test
    void testBasisConsturctor() {
        Cell cell = new Cell();
        assertNull(cell.result);
        assertTrue(cell.suggestions.size() == 9);
        for (int i = 1; i <= 9; i++) {
            assertTrue(cell.suggestions.contains(i));
        }
        assertFalse(cell.isSolved());
    }

    @Test
    void testConsturctorWithNull() {
        Cell cell = new Cell(null);
        assertNull(cell.result);
        assertTrue(cell.suggestions.size() == 9);
        for (int i = 1; i <= 9; i++) {
            assertTrue(cell.suggestions.contains(i));
        }
        assertFalse(cell.isSolved());
    }

    @Test
    void testConsturctorWithValidNumber() {
        Cell cell = new Cell(validNum);
        assertEquals(validNum, cell.result);
        assertTrue(cell.suggestions.size() == 1
                && cell.suggestions.contains(validNum));
        Assertions.assertTrue(cell.isSolved());
    }

    @Test
    void testConsturctorWithInvalidNumber() {
        IllegalArgumentException exception
                = assertThrows(IllegalArgumentException.class, () -> {
                    Cell cell = new Cell(invalidNum);
                });
        String expectedMessage = "between 1 and 9";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testRemoveOneSuggestion() {
        Cell cell1 = new Cell();
        cell1.removeOneSuggestion(validNum);
        assertFalse(cell1.isSolved());
        assertFalse(cell1.suggestions.contains(validNum));
        Cell cell2 = new Cell(validNum);
        cell2.removeOneSuggestion(validNum);
        assertTrue(cell2.isSolved());
        Cell cell3 = new Cell();
        Set<Integer> fullSet = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
        fullSet.forEach(i -> {
            cell3.removeOneSuggestion(i);
        });
        assertEquals(cell3.suggestions.iterator().next(), cell3.result);
    }

    @Test
    void testEquality() {
        Cell cell1 = new Cell(validNum);
        Cell cell2 = new Cell(validNum);
        assertEquals(cell1, cell2);
    }

    @AfterAll
    static void tearDown() {
        validNum = null;
        invalidNum = null;
    }

}
