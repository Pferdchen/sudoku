package com.demo.sudoku.test.model;

import static org.junit.Assert.assertArrayEquals;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.junit.BeforeClass;
import org.junit.Test;

import com.demo.sudoku.model.SudokuContainer;

public class SudokuContainerTest {
	private final static int[][] template = { { 5, 3, 0, 0, 7, 0, 0, 0, 0 },
			{ 6, 0, 0, 1, 9, 5, 0, 0, 0 }, { 0, 9, 8, 0, 0, 0, 0, 6, 0 },
			{ 8, 0, 0, 0, 6, 0, 0, 0, 3 }, { 4, 0, 0, 8, 0, 3, 0, 0, 1 },
			{ 7, 0, 0, 0, 2, 0, 0, 0, 6 }, { 0, 6, 0, 0, 0, 0, 2, 8, 0 },
			{ 0, 0, 0, 4, 1, 9, 0, 0, 5 }, { 0, 0, 0, 0, 8, 0, 0, 7, 9 } };
	private final static String templatePathInString = "./templates/template1.txt";
	private final static String tempString = "530070000" + "600195000"
			+ "098000060" + "800060003" + "400803001" + "700020006"
			+ "060000280" + "000419005" + "000080079";

	private static SudokuContainer sc;

	@BeforeClass
	public static void init() {
		Path templatePath = FileSystems.getDefault().getPath(
				templatePathInString);
		sc = new SudokuContainer(templatePath);
	}

	@Test
	public void testGetSudoku() {
		assertArrayEquals(template, sc.getSudoku());
		assertArrayEquals(template,
				new SudokuContainer(tempString, false).getSudoku());
	}
}
