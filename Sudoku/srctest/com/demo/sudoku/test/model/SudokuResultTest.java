package com.demo.sudoku.test.model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.demo.sudoku.Utility;
import com.demo.sudoku.model.SudokuResult;
import com.demo.sudoku.model.SudokuTemplate;

public class SudokuResultTest {
	private final static int[][] template = { { 5, 3, 0, 0, 7, 0, 0, 0, 0 },
			{ 6, 0, 0, 1, 9, 5, 0, 0, 0 }, { 0, 9, 8, 0, 0, 0, 0, 6, 0 },
			{ 8, 0, 0, 0, 6, 0, 0, 0, 3 }, { 4, 0, 0, 8, 0, 3, 0, 0, 1 },
			{ 7, 0, 0, 0, 2, 0, 0, 0, 6 }, { 0, 6, 0, 0, 0, 0, 2, 8, 0 },
			{ 0, 0, 0, 4, 1, 9, 0, 0, 5 }, { 0, 0, 0, 0, 8, 0, 0, 7, 9 } };
	private final static int[][] result = { { 5, 3, 4, 6, 7, 8, 9, 1, 2 },
			{ 6, 7, 2, 1, 9, 5, 3, 4, 8 }, { 1, 9, 8, 3, 4, 2, 5, 6, 7 },
			{ 8, 5, 9, 7, 6, 1, 4, 2, 3 }, { 4, 2, 6, 8, 5, 3, 7, 9, 1 },
			{ 7, 1, 3, 9, 2, 4, 8, 5, 6 }, { 9, 6, 1, 5, 3, 7, 2, 8, 4 },
			{ 2, 8, 7, 4, 1, 9, 6, 3, 5 }, { 3, 4, 5, 2, 8, 6, 1, 7, 9 } };
//	private final static String templatePathInString = "./templates/template1.txt";

	@Rule
    public TemporaryFolder testFolder = new TemporaryFolder();
	private static File templateFile;
	
	@Before
    public void createTestData() throws IOException {
		templateFile = testFolder.newFile("template.txt");
        BufferedWriter out = new BufferedWriter(new FileWriter(templateFile));
        out.write("53  7    \n");
        out.write("6  195   \n");
        out.write(" 98    6 \n");
        out.write("8   6   3\n");
        out.write("4  8 3  1\n");
        out.write("7   2   6\n");
        out.write(" 6    28 \n");
        out.write("   419  5\n");
        out.write("    8  79\n");
        out.close();
    }
	
	@Test
	public void testGetSudoku() throws IOException {
		SudokuResult r1 = new SudokuResult(template);
		assertArrayEquals(result, r1.getSudoku());
		
		Path templatePath = templateFile.toPath();
		SudokuTemplate st = new SudokuTemplate(templatePath);
		assertFalse(Utility.isCorrect2(st.getSudoku()));
		SudokuResult r2 = new SudokuResult(st);
		assertTrue(Utility.isCorrect2(r2.getSudoku()));
		assertArrayEquals(result, r2.getSudoku());
	}

	@After
    public void cleanUp() {
//       assertThat(templateFile, matcher);
    }
}
