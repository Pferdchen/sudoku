package com.demo.sudoku2.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

public class Normalizer {

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
            if (Character.isDigit(c)) {
                intArr[index] = Character.getNumericValue(c);
                index++;
            } else if (c == ' ') {
                intArr[index] = null;
                index++;
            }
        }
        return intArr;
    }

    public static Integer[] normalize(String puzzle) {
        Integer[] intArr = new Integer[81];
        if (hasOneLineWithoutComma(puzzle)) {
            char[] charArr = puzzle.toCharArray();
            for (int i = 0; i < charArr.length; i++) {
                char c = charArr[i];
                intArr[i] = Character.isDigit(c)
                        ? Character.getNumericValue(c) : null;
            }
            return intArr;
        }
        if (hasOneLineWithComma(puzzle)) {
            String[] strArr = puzzle.split(COMMA_REGEX);
            for (int i = 0; i < strArr.length; i++) {
                String str = strArr[i];
                intArr[i] = isDigit(str)
                        ? Integer.parseInt(str) : null;
            }
            return intArr;
        }
        if (hasNineLinesWithoutComma(puzzle)) {
            String[] lineArr = puzzle.split(LINE_BREAK_REGEX);
            for (int i = 0; i < lineArr.length; i++) {
                char[] charArr = lineArr[i].toCharArray();
                for (int j = 0; j < charArr.length; j++) {
                    char c = charArr[j];
                    intArr[i * 9 + j] = Character.isDigit(c)
                            ? Character.getNumericValue(c) : null;
                }
            }
            return intArr;
        }
        if (hasNineLinesWithComma(puzzle)) {
            String[] lineArr = puzzle.split(LINE_BREAK_REGEX);
            for (int i = 0; i < lineArr.length; i++) {
                String[] strArr = lineArr[i].split(COMMA_REGEX);
                for (int j = 0; j < strArr.length; j++) {
                    String str = strArr[j];
                    intArr[i * 9 + j] = isDigit(str)
                            ? Integer.parseInt(str) : null;
                }
            }
            return intArr;
        }
        throw new IllegalArgumentException("The given puzzle is not valid!");
    }

    private static final Pattern ONE_LINE_WITHOUT_COMMA_PATTERN
            = Pattern.compile("^([1-9]|\\s){81}$");

    private static boolean hasOneLineWithoutComma(String puzzle) {
        return ONE_LINE_WITHOUT_COMMA_PATTERN.matcher(puzzle).matches();
    }

    private static final Pattern ONE_LINE_WITH_COMMA_PATTERN
            = Pattern.compile("^(([1-9]|\\s),){80}([1-9]|\\s)$");

    private static boolean hasOneLineWithComma(String puzzle) {
        return ONE_LINE_WITH_COMMA_PATTERN.matcher(puzzle).matches();
    }

    private static final Pattern NINE_LINES_WITHOUT_COMMA_PATTERN
            = Pattern.compile("^(([1-9]|\\s){9}(\\r\\n|\\n|\\r)){8}([1-9]|\\s){9}$");

    private static boolean hasNineLinesWithoutComma(String puzzle) {
        return NINE_LINES_WITHOUT_COMMA_PATTERN.matcher(puzzle).matches();
    }

    private static final Pattern NINE_LINES_WITH_COMMA_PATTERN
            = Pattern.compile("^((([1-9]|\\s),){8}([1-9]|\\s)(\\r\\n|\\n|\\r)){8}(([1-9]|\\s),){8}([1-9]|\\s)$");

    private static boolean hasNineLinesWithComma(String puzzle) {
        return NINE_LINES_WITH_COMMA_PATTERN.matcher(puzzle).matches();
    }

    private static final String COMMA_REGEX = ",";

    private static final String LINE_BREAK_REGEX = "\\r\\n|\\n|\\r";

    private static final Pattern SINGLE_DIGIT_PATTERN
            = Pattern.compile("^[1-9]$");// except 0

    private static boolean isDigit(String str) {
        return SINGLE_DIGIT_PATTERN.matcher(str).matches();
    }

}
