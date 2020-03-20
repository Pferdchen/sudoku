package com.demo.sudoku2.util;

import static com.demo.sudoku2.util.Indexer.columnIndex;
import static com.demo.sudoku2.util.Indexer.regionIndex;
import static com.demo.sudoku2.util.Indexer.rowIndex;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class IndexerTest {

    /* sudoku index
    #0 #1 #2 #3 #4 #5 #6 #7 #8
    
#0   0, 1, 2, 3, 4, 5, 6, 7, 8,
#1   9,10,11,12,13,14,15,16,17,
#2  18,19,20,21,22,23,24,25,26,
#3  27,28,29,30,31,32,33,34,35,
#4  36,37,38,39,40,41,42,43,44,
#5  45,46,47,48,49,50,51,52,53,
#6  54,55,56,57,58,59,60,61,62,
#7  63,64,65,66,67,68,69,70,71,
#8  72,73,74,75,76,77,78,79,80
     */
    @Test
    void testRowIndex() {
        assertEquals(0, rowIndex(0));
        assertEquals(0, rowIndex(1));
        assertEquals(0, rowIndex(8));
        assertEquals(1, rowIndex(9));
        assertEquals(3, rowIndex(27));
        assertEquals(5, rowIndex(49));
        assertEquals(8, rowIndex(80));
    }

    @Test
    void testColumnIndex() {
        assertEquals(0, columnIndex(0));
        assertEquals(1, columnIndex(1));
        assertEquals(8, columnIndex(8));
        assertEquals(0, columnIndex(9));
        assertEquals(0, columnIndex(27));
        assertEquals(4, columnIndex(49));
        assertEquals(8, columnIndex(80));
    }

    @Test
    void testRegionIndex() {
        assertEquals(0, regionIndex(0));
        assertEquals(0, regionIndex(1));
        assertEquals(2, regionIndex(8));
        assertEquals(0, regionIndex(9));
        assertEquals(3, regionIndex(27));
        assertEquals(4, regionIndex(49));
        assertEquals(8, regionIndex(80));
    }

}
