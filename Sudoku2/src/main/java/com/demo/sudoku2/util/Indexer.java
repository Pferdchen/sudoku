package com.demo.sudoku2.util;

public class Indexer {

    public static final int SIZE = 9;

    public static int rowIndex(int i) {
        return i / 9;
    }

    public static int columnIndex(int i) {
        return i % 9;
    }

    public static int regionIndex(int i) {
        return ((i / 9 / 3) * 3) + ((i % 9) / 3);
    }

}
