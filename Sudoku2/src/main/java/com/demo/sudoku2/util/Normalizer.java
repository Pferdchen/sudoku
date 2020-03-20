package com.demo.sudoku2.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.lang3.StringUtils;

public class Normalizer {

    private static final String ONE_LINE_WITHOUT_COMMA
            = "([1-9]|\\s){81}";
    private static final String ONE_LINE_WITH_COMMA
            = "(([1-9]|\\s),){80}([1-9]|\\s)";
    private static final String NINE_LINE_WITHOUT_COMMA
            = "(([1-9]|\\s){9}(\\r\\n|\\n|\\r)){8}([1-9]|\\s){9}";
    private static final String NINE_LINE_WITH_COMMA
            = "((([1-9]|\\s),){8}([1-9]|\\s)(\\r\\n|\\n|\\r)){8}(([1-9]|\\s),){8}([1-9]|\\s)";
    private static final String COMMA = ",";
    private static final String LINE_BREAK = "\\r\\n|\\n|\\r";

    public static Integer[] normalize(String puzzle) {
        Integer[] intArr = new Integer[81];
        if (puzzle.matches(ONE_LINE_WITHOUT_COMMA)) {
            char[] charArr = puzzle.toCharArray();
            for (int i = 0; i < charArr.length; i++) {
                char c = charArr[i];
                int numericValue = Character.getNumericValue(c);
                intArr[i] = numericValue > 0 ? numericValue : null;
            }
            return intArr;
        }
        if (puzzle.matches(ONE_LINE_WITH_COMMA)) {
            String[] strArr = puzzle.split(COMMA);
            for (int i = 0; i < strArr.length; i++) {
                String str = strArr[i];
                intArr[i] = StringUtils.isNumeric(str)
                        ? Integer.parseInt(str) : null;
            }
            return intArr;
        }
        if (puzzle.matches(NINE_LINE_WITHOUT_COMMA)) {
            String[] lineArr = puzzle.split(LINE_BREAK);
            for (int i = 0; i < lineArr.length; i++) {
                char[] charArr = lineArr[i].toCharArray();
                for (int j = 0; j < charArr.length; j++) {
                    char c = charArr[j];
                    int numericValue = Character.getNumericValue(c);
                    intArr[i * 9 + j] = numericValue > 0 ? numericValue : null;
                }
            }
            return intArr;
        }
        if (puzzle.matches(NINE_LINE_WITH_COMMA)) {
            String[] lineArr = puzzle.split(LINE_BREAK);
            for (int i = 0; i < lineArr.length; i++) {
                String[] strArr = lineArr[i].split(COMMA);
                for (int j = 0; j < strArr.length; j++) {
                    String str = strArr[j];
                    intArr[i * 9 + j] = StringUtils.isNumeric(str)
                            ? Integer.parseInt(str) : null;
                }
            }
            return intArr;
        }
        throw new IllegalArgumentException("The given puzzle is not valid!");
    }

    public static Integer[] normalize(File puzzle) throws IOException {
        try (InputStream is = new FileInputStream(puzzle)) {
            return normalize(is);
        }
    }

    public static Integer[] normalize(InputStream puzzle) throws IOException {
        Integer[] intArr = new Integer[81];
        int index = 0;
        int i;
        while ((i = puzzle.read()) != -1) {
            if (index == 81) {// The amount of int and whitespace should be 81.
                throw new IllegalArgumentException("The given puzzle is not valid!");
            }
            char c = (char) i;
            int numericValue = Character.getNumericValue(c);
            if (numericValue > 0) {
                intArr[index] = numericValue;
                index++;
            } else if (c == ' ') {
                intArr[index] = null;
                index++;
            }
        }
        return intArr;
    }

}
