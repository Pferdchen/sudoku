package com.demo.sudoku.model;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SudokuResult implements Sudoku {

	private int[][] result;

	public SudokuResult(SudokuTemplate template) {
		this(template.getSudoku());
	}

	public SudokuResult(int[][] template) {
		int[][] result = template.clone();

		@SuppressWarnings("unchecked")
		Set<Integer>[][] possibleValues = new HashSet[SIZE][SIZE];

		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (result[i][j] != 0) {
					Set<Integer> oneElementSet = new HashSet<>();
					oneElementSet.add(result[i][j]);
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
					moreElementsSet.remove(result[i][k]);// Remove on horizontal
					moreElementsSet.remove(result[k][j]);// Remove on vertical
				}

				// Remove squared elements from the Set
				for (int r = (i / 3) * 3; r < (i / 3) * 3 + 3; r++) {
					for (int s = (j / 3) * 3; s < (j / 3) * 3 + 3; s++) {
						moreElementsSet.remove(result[r][s]);
					}
				}

				possibleValues[i][j] = moreElementsSet;

				reduceSets(possibleValues, result, i, j);
			}
		}

		this.result = result;
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

	@Override
	public int[][] getSudoku() {
		return this.result;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (result[i][j] == 0)
					sb.append(" ");
				else
					sb.append(Integer.toString(result[i][j]));
			}
		}
		return sb.toString();
	}
}
