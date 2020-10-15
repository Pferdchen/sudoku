package com.demo.sudoku2.util;

import static com.demo.sudoku2.util.Normalizer.normalize;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class NormalizerTest {

    private static String sudokuPuzzle;
    private static Integer[] expectedPuzzle;

    @BeforeAll
    static void init() {
        sudokuPuzzle = "puzzles/puzzle1.txt";
        expectedPuzzle = new Integer[]{
            5, 3, null, null, 7, null, null, null, null,
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
    void testNormalizeString() {
        String ONE_LINE_WITHOUT_COMMA
                = "53  7    "
                + "6  195   "
                + " 98    6 "
                + "8   6   3"
                + "4  8 3  1"
                + "7   2   6"
                + " 6    28 "
                + "   419  5"
                + "    8  79";
        String ONE_LINE_WITH_COMMA
                = "5,3, , ,7, , , , ,"
                + "6, , ,1,9,5, , , ,"
                + " ,9,8, , , , ,6, ,"
                + "8, , , ,6, , , ,3,"
                + "4, , ,8, ,3, , ,1,"
                + "7, , , ,2, , , ,6,"
                + " ,6, , , , ,2,8, ,"
                + " , , ,4,1,9, , ,5,"
                + " , , , ,8, , ,7,9";
        String NINE_LINE_WITHOUT_COMMA
                = "53  7    " + "\n"
                + "6  195   " + "\n"
                + " 98    6 " + "\n"
                + "8   6   3" + "\n"
                + "4  8 3  1" + "\n"
                + "7   2   6" + "\n"
                + " 6    28 " + "\n"
                + "   419  5" + "\n"
                + "    8  79";
        String NINE_LINE_WITH_COMMA
                = "5,3, , ,7, , , , " + "\n"
                + "6, , ,1,9,5, , , " + "\n"
                + " ,9,8, , , , ,6, " + "\n"
                + "8, , , ,6, , , ,3" + "\n"
                + "4, , ,8, ,3, , ,1" + "\n"
                + "7, , , ,2, , , ,6" + "\n"
                + " ,6, , , , ,2,8, " + "\n"
                + " , , ,4,1,9, , ,5" + "\n"
                + " , , , ,8, , ,7,9";
        assertArrayEquals(expectedPuzzle, normalize(ONE_LINE_WITHOUT_COMMA));
        assertArrayEquals(expectedPuzzle, normalize(ONE_LINE_WITH_COMMA));
        assertArrayEquals(expectedPuzzle, normalize(NINE_LINE_WITHOUT_COMMA));
        assertArrayEquals(expectedPuzzle, normalize(NINE_LINE_WITH_COMMA));
    }

    @Test
    void testNormalizeFile() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader
                .getResource(sudokuPuzzle).getFile());
        assertArrayEquals(expectedPuzzle, normalize(file));
    }

    @Test
    void testNormalizeStream() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        try ( InputStream is = classLoader
                .getResourceAsStream(sudokuPuzzle)) {
            assertArrayEquals(expectedPuzzle, normalize(is));
        }
    }

    @Test
    void testNormalizePath() throws IOException, URISyntaxException {
        Path path = Paths.get(getClass().getClassLoader()
                .getResource(sudokuPuzzle).toURI());
        assertArrayEquals(expectedPuzzle, normalize(path));
    }

    @AfterAll
    static void tearDown() {
        sudokuPuzzle = null;
        expectedPuzzle = null;
    }
}
