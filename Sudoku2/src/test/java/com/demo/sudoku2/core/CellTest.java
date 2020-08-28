package com.demo.sudoku2.core;

import java.util.Random;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class CellTest {

    @Test
    void testBasisConsturctor() {
        Cell cell = new Cell();
        assertNull(cell.result);
        assertTrue(cell.suggestions.size() == 9);
        for (int i = 1; i <= 9; i++) {
            assertTrue(cell.suggestions.contains(i));
        }
        assertTrue(cell.isEmpty());
    }

    @Test
    void testConsturctorWithNull() {
        Cell cell = new Cell(null);
        assertNull(cell.result);
        assertTrue(cell.suggestions.size() == 9);
        for (int i = 1; i <= 9; i++) {
            assertTrue(cell.suggestions.contains(i));
        }
        assertTrue(cell.isEmpty());
    }

    @Test
    void testConsturctorWithValidNumber() {
        // Obtain a random number in range [1, 9].
        int validNum = new Random().nextInt(9) + 1;
        Cell cell = new Cell(validNum);
        assertEquals(validNum, cell.result);
        assertTrue(cell.suggestions.size() == 1
                && cell.suggestions.contains(validNum));
        Assertions.assertFalse(cell.isEmpty());
    }

    @Test
    void testConsturctorWithInvalidNumber() {
        IllegalArgumentException exception
                = assertThrows(IllegalArgumentException.class, () -> {
                    // Obtain a random number outside range [1, 9].
                    int invalidNum = 0;
                    do {
                        invalidNum = new Random().nextInt();
                    } while (invalidNum >= 1 && invalidNum <= 9);
                    Cell cell = new Cell(invalidNum);
                });
        String expectedMessage = "between 1 and 9";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
    /*
    @Test
    void testEquality() {
        Cell cell1 = new Cell(4);
        Cell cell2 = new Cell(4);
        assertEquals(cell1, cell2);
    }
     */
}
