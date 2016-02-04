package com.demo.sudoku.test.model;

import static org.junit.Assert.assertArrayEquals;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.junit.BeforeClass;
import org.junit.Test;

import com.demo.sudoku.model.SudokuTemplate;

public class SudokuTemplateTest {
	private final static int[][] template = { { 5, 3, 0, 0, 7, 0, 0, 0, 0 },
			{ 6, 0, 0, 1, 9, 5, 0, 0, 0 }, { 0, 9, 8, 0, 0, 0, 0, 6, 0 },
			{ 8, 0, 0, 0, 6, 0, 0, 0, 3 }, { 4, 0, 0, 8, 0, 3, 0, 0, 1 },
			{ 7, 0, 0, 0, 2, 0, 0, 0, 6 }, { 0, 6, 0, 0, 0, 0, 2, 8, 0 },
			{ 0, 0, 0, 4, 1, 9, 0, 0, 5 }, { 0, 0, 0, 0, 8, 0, 0, 7, 9 } };
	private final static String templatePathInString = "./templates/template1.txt";

	private static SudokuTemplate st;

	@BeforeClass
	public static void init() {
		Path templatePath = FileSystems.getDefault().getPath(
				templatePathInString);
		st = new SudokuTemplate(templatePath);
	}

	@Test
	public void testGetSudoku() {
		assertArrayEquals(template, st.getSudoku());
	}

}
