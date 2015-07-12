package com.demo.sudoku.model;

import java.util.Iterator;

import com.demo.sudoku.Utility;

public class SudokuFullResult implements SudokuFull {

	private Cell[][] result;

	public SudokuFullResult(SudokuTemplate template) {
		this(template.getSudoku());
	}

	public SudokuFullResult(int[][] template) {
		Cell[][] result = Utility.initialize(template);
		for (int i = 0; i < SIZE; i++) {
			for (int j = 0; j < SIZE; j++) {
				if (result[i][j].getNumber() != null) {// terminate one
														// element set
					result[i][j].getPossibleNumbers().add(
							result[i][j].getNumber());
					continue;
				}

				int n = 0;
				while (n < SIZE) {// Initialize Set with full elements
					result[i][j].getPossibleNumbers().add(++n);
				}

				// Remove other axis elements from the Set
				for (int k = 0; k < SIZE; k++) {// e.g. [i][j]=[0][3]=6
					result[i][j].getPossibleNumbers().remove(
							result[i][k].getNumber());// Remove on horizontal
					result[i][j].getPossibleNumbers().remove(
							result[k][j].getNumber());// Remove on vertical
				}

				// Remove squared elements from the Set
				for (int r = (i / 3) * 3; r < (i / 3) * 3 + 3; r++) {
					for (int s = (j / 3) * 3; s < (j / 3) * 3 + 3; s++) {
						result[i][j].getPossibleNumbers().remove(
								result[r][s].getNumber());
					}
				}

				reduceSets(result, i, j);
			}
		}

		this.result = result;
	}

	private void reduceSets(Cell[][] result, int m, int n) {
		if (result[m][n].getPossibleNumbers().size() == 1
				&& result[m][n].getNumber() == null) {
			Iterator<Integer> iter = result[m][n].getPossibleNumbers()
					.iterator();
			result[m][n].setNumber(iter.next());
			reduceSetsOnAxis(result, m, n);
			reduceSetsInSquare(result, m, n);
		}
	}

	private void reduceSetsOnAxis(Cell[][] result, int i, int j) {
		for (int k = 0; k < 9; k++) {
			if (k != j && result[i][k].getPossibleNumbers().size() > 1) {
				result[i][k].getPossibleNumbers().remove(
						result[i][j].getNumber());
				reduceSets(result, i, k);
			}
			if (k != i && result[k][j].getPossibleNumbers().size() > 1) {
				result[k][j].getPossibleNumbers().remove(
						result[i][j].getNumber());
				reduceSets(result, k, j);
			}
		}
	}

	private void reduceSetsInSquare(Cell[][] result, int i, int j) {
		for (int r = (i / 3) * 3; r < (i / 3) * 3 + 3; r++) {
			for (int s = (j / 3) * 3; s < (j / 3) * 3 + 3; s++) {
				if (r != i && s != j
						&& result[r][s].getPossibleNumbers().size() > 1) {
					result[r][s].getPossibleNumbers().remove(
							result[i][j].getNumber());
					reduceSets(result, r, s);
				}
			}
		}
	}

	@Override
	public Cell[][] getSudoku() {
		return this.result;
	}

}
