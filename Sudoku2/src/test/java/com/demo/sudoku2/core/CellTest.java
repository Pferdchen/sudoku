package com.demo.sudoku2.core;

import java.util.Random;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CellTest {

    private int num;
    private Cell cell1;
    private Cell cell2;

    @BeforeEach
    void init() {
        // Obtain a number between [0 - 8] and plus 1.
        num = new Random().nextInt(9) + 1;
        cell1 = new Cell(num);
        cell2 = new Cell(null);
    }

    @Test
    void testConsturctor() {
        assertEquals(num, cell1.result);
        assertTrue(cell1.suggestions.size() == 1
                && cell1.suggestions.contains(num));
        assertNull(cell2.result);
        assertTrue(cell2.suggestions.size() == 9);
        for (int i = 1; i <= 9; i++) {
            assertTrue(cell2.suggestions.contains(i));
        }
    }

    @AfterEach
    void tearDown() {
        cell1 = null;
        cell2 = null;
    }
}
