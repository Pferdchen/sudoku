package com.demo.sudoku.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SudokuContainer implements Sudoku {

	private int[][] sudoku;

	/**
	 * Create a leer SudokuContainer.
	 */
	public SudokuContainer() {
		this.sudoku = new int[SIZE][SIZE];
	}

	/**
	 * Read a Sudoku from a template file.
	 * 
	 * @param templatePath
	 */
	public SudokuContainer(Path templatePath) {
		try {
			List<String> lines = new ArrayList<String>();
			for (String line : Files.readAllLines(templatePath))
				lines.add(line);

			boolean isSudoku = true;
			if (lines.size() != SIZE) {
				isSudoku = false;
			} else {
				for (int i = 0; i < SIZE; i++) {
					if (lines.get(i).length() != SIZE) {
						isSudoku = false;
						break;
					}
				}
			}

			if (isSudoku)
				setTemplate(lines);
			else {
				this.sudoku = null;
			}
		} catch (IOException e) {
			System.out
					.println("An I/O error occurs reading from the file or a malformed or unmappable byte sequence is read");
		}
	}

	/**
	 * Create Sudoku from source.
	 * 
	 * @param src
	 *            is either a Sudoku in a line or a path of Sudoku.
	 * @param srcIsPath
	 */
	public SudokuContainer(String src, boolean srcIsPath) {
		if (srcIsPath) {
			try (FileInputStream fin = new FileInputStream(src);
					BufferedReader br = new BufferedReader(
							new InputStreamReader(fin));) {
				boolean isSudoku = true;
				List<String> lines = new ArrayList<String>();
				while (br.ready()) {
					lines.add(br.readLine());
				}
				int row = lines.size();
				if (row == 0)
					isSudoku = false;

				int length = lines.get(0).length();
				for (int i = 1; i < row; i++) {
					if (lines.get(i).length() != length)
						isSudoku = false;
				}// '\n'=10,' '=32

				if (isSudoku)
					setTemplate(lines);
				else {
					this.sudoku = null;
				}
			} catch (FileNotFoundException ex) {
				System.out.println("Input file is not found");
			} catch (IOException ex) {
				System.out.println("Can't read the file");
			}
		} else {
			if (src.length() == SIZE * SIZE) {
				char[] ca = src.toCharArray();
				this.sudoku = new int[SIZE][SIZE];
				for (int i = 0; i < SIZE; i++) {
					for (int j = 0; j < SIZE; j++) {
						this.sudoku[i][j] = Integer.parseInt(String
								.valueOf(ca[i * SIZE + j]));
					}
				}
			} else
				this.sudoku = null;
		}
	}

	private void setTemplate(List<String> lines) {
		int[][] template = new int[SIZE][SIZE];
		int i = 0;
		for (String line : lines) {
			for (int j = 0; j < SIZE; j++) {
				char ch = line.charAt(j);
				if (ch == ' ')
					template[i][j] = 0;
				else
					template[i][j] = Character.getNumericValue(ch);
			}
			i++;
		}
		this.sudoku = template;
	}

	@Override
	public int[][] getSudoku() {
		return this.sudoku;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (sudoku[i][j] == 0)
					sb.append(" ");
				else
					sb.append(Integer.toString(sudoku[i][j]));
			}
		}
		return sb.toString();
	}

	public void calculate() {
//		int[][] clone = this.sudoku.clone();

		@SuppressWarnings("unchecked")
		Set<Integer>[][] possibleValues = new HashSet[SIZE][SIZE];

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (sudoku[i][j] != 0) {
					Set<Integer> oneElementSet = new HashSet<>();
					oneElementSet.add(sudoku[i][j]);
					possibleValues[i][j] = oneElementSet;
					continue;
				}

				Set<Integer> moreElementsSet = new HashSet<Integer>();
				int n = 0;
				while (n < SIZE) {// Initialize Set with full elements
					moreElementsSet.add(++n);
				}

				// Remove other axis elements from the Set
				for (int k = 0; k < SIZE; k++) {// e.g. [i][j]=[0][3]=6
					moreElementsSet.remove(sudoku[i][k]);// Remove on horizontal
					moreElementsSet.remove(sudoku[k][j]);// Remove on vertical
				}

				// Remove squared elements from the Set
				for (int r = (i / 3) * 3; r < (i / 3) * 3 + 3; r++) {
					for (int s = (j / 3) * 3; s < (j / 3) * 3 + 3; s++) {
						moreElementsSet.remove(sudoku[r][s]);
					}
				}

				possibleValues[i][j] = moreElementsSet;

				reduceSets(possibleValues, sudoku, i, j);
			}
		}

//		this.sudoku = clone;
	}

	private void reduceSets(Set<Integer>[][] possibleValues, int[][] result,
			int m, int n) {
		if (possibleValues[m][n].size() == 1 && result[m][n] == 0) {
			Iterator<Integer> iter = possibleValues[m][n].iterator();
			result[m][n] = iter.next();
			reduceSetsOnAxis(possibleValues, result, m, n);
			reduceSetsInSquare(possibleValues, result, m, n);
		}
	}

	private void reduceSetsOnAxis(Set<Integer>[][] possibleValues,
			int[][] result, int i, int j) {
		for (int k = 0; k < 9; k++) {
			if (k != j && possibleValues[i][k] != null
					&& possibleValues[i][k].size() > 1) {
				possibleValues[i][k].remove(result[i][j]);
				reduceSets(possibleValues, result, i, k);
			}
			if (k != i && possibleValues[k][j] != null
					&& possibleValues[k][j].size() > 1) {
				possibleValues[k][j].remove(result[i][j]);
				reduceSets(possibleValues, result, k, j);
			}
		}
	}

	private void reduceSetsInSquare(Set<Integer>[][] possibleValues,
			int[][] result, int i, int j) {
		for (int r = (i / 3) * 3; r < (i / 3) * 3 + 3; r++) {
			for (int s = (j / 3) * 3; s < (j / 3) * 3 + 3; s++) {
				if (r != i && s != j && possibleValues[r][s] != null
						&& possibleValues[r][s].size() > 1) {
					possibleValues[r][s].remove(result[i][j]);
					reduceSets(possibleValues, result, r, s);
				}
			}
		}
	}

	
}
