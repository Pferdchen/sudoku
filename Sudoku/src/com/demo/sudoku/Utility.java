package com.demo.sudoku;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.demo.sudoku.model.Cell;
import com.demo.sudoku.model.Sudoku;
import com.demo.sudoku.model.SudokuFull;

public class Utility {

	/**
	 * Calculate n+(n-1)+...+1.
	 * 
	 * @param n
	 * @return sum of n
	 */
	public static int sum(int n) {
		if (n < 1)
			return 0;

		return n * (n + 1) / 2;
	}

	/**
	 * Calculate n*(n-1)*...*1.
	 * 
	 * @param n
	 * @return n!
	 */
	public static int fac(int n) {
		if (n < 1)
			return 0;

		int fac = 1;
		while (n > 0) {
			fac *= n;
			n--;
		}
		return fac;
	}

	/**
	 * If all the numbers are not {@code 0}, return {@code true}. Otherwise,
	 * return {@code false}.
	 * 
	 * @param sudoku
	 * @return
	 */
	public static boolean isResolved(int[][] sudoku) {
		final int SIZE = sudoku.length;
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (sudoku[i][j] == 0)
					return false;
			}
		}
		return true;
	}

	/**
	 * If each column, each row, and each of the nine 3×3 sub-grids contains all
	 * of the digits from 1 to 9.
	 * 
	 * @param sudoku
	 * @return
	 */
	public static boolean isCorrect(int[][] sudoku) {
		if (sudoku == null)
			return false;

		if (sudoku.length != Sudoku.SIZE)
			return false;

		int sum = sum(Sudoku.SIZE);
		int fac = fac(Sudoku.SIZE);

		int rowSum;
		int rowPro;
		int columnSum;
		int columnPro;
		for (int i = 0; i < Sudoku.SIZE; i++) {
			if (sudoku[i].length != Sudoku.SIZE)
				return false;

			rowSum = 0;
			rowPro = 1;
			columnSum = 0;
			columnPro = 1;
			for (int j = 0; j < Sudoku.SIZE; j++) {
				rowSum += sudoku[i][j];
				rowPro *= sudoku[i][j];
				columnSum += sudoku[j][i];
				columnPro *= sudoku[j][i];
			}
			if (rowSum != sum || columnSum != sum || rowPro != fac
					|| columnPro != fac)
				return false;
		}

		int squareSum;
		int squarePro;
		for (int s = 0; s < 3; s++) {
			for (int t = 0; t < 3; t++) {
				squareSum = 0;
				squarePro = 1;
				for (int m = 3 * s; m < 3 * s + 3; m++) {
					for (int n = 3 * t; n < 3 * t + 3; n++) {
						squareSum += sudoku[m][n];
						squarePro *= sudoku[m][n];
					}
				}
				if (squareSum != sum || squarePro != fac)
					return false;
			}
		}

		return true;
	}

	/**
	 * If each column, each row, and each of the nine 3×3 sub-grids contains all
	 * of the digits from 1 to 9.
	 * 
	 * @param sudoku
	 * @return
	 */
	public static boolean isCorrect2(int[][] sudoku) {
		if (sudoku == null)
			return false;

		if (sudoku.length != Sudoku.SIZE)
			return false;

		Integer[] numbers = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		Set<Integer> numberSet;
		for (int i = 0; i < Sudoku.SIZE; i++) {
			if (sudoku[i].length != Sudoku.SIZE)
				return false;

			numberSet = new HashSet<Integer>(Arrays.asList(numbers));
			for (int j = 0; j < Sudoku.SIZE; j++) {
				numberSet.remove(sudoku[i][j]);
			}
			if (!numberSet.isEmpty())
				return false;

			numberSet = new HashSet<Integer>(Arrays.asList(numbers));
			for (int j = 0; j < Sudoku.SIZE; j++) {
				numberSet.remove(sudoku[j][i]);
			}
			if (!numberSet.isEmpty())
				return false;
		}

		for (int s = 0; s < 3; s++) {
			for (int t = 0; t < 3; t++) {
				numberSet = new HashSet<Integer>(Arrays.asList(numbers));
				for (int m = 3 * s; m < 3 * s + 3; m++) {
					for (int n = 3 * t; n < 3 * t + 3; n++) {
						numberSet.remove(sudoku[m][n]);
					}
				}
				if (!numberSet.isEmpty())
					return false;
			}
		}

		return true;
	}

	public static int[][] normalize(Cell[][] sudokuFull) {
		int[][] sudoku = new int[SudokuFull.SIZE][SudokuFull.SIZE];
		for (int i = 0; i < SudokuFull.SIZE; i++) {
			for (int j = 0; j < SudokuFull.SIZE; j++) {
				sudoku[i][j] = sudokuFull[i][j].getNumber() != null ? sudokuFull[i][j]
						.getNumber().intValue() : 0;
			}
		}

		return sudoku;
	}

	public static Cell[][] initialize(int[][] template) {
		Cell[][] templateFull = new Cell[SudokuFull.SIZE][SudokuFull.SIZE];
		for (int i = 0; i < SudokuFull.SIZE; i++) {
			for (int j = 0; j < SudokuFull.SIZE; j++) {
				templateFull[i][j] = new Cell();
				if (template[i][j] != 0){
					templateFull[i][j].setNumber(Integer.valueOf(template[i][j]));}
				else
					templateFull[i][j].setNumber(null);
			}
		}

		return templateFull;
	}
}
