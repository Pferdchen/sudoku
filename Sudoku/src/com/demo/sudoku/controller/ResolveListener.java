package com.demo.sudoku.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import com.demo.sudoku.model.Cell;
import com.demo.sudoku.view.SudokuBoardDisplay;

public class ResolveListener implements ActionListener {
	
	private static final int SIZE = 9;
	
	private SudokuBoardDisplay sudokuBoard;
	
	private Cell[][] result;
	
	public ResolveListener(SudokuBoardDisplay sudokuBoard, Cell[][] result) {
		this.sudokuBoard = sudokuBoard;
		this.result = result;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
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
		
		
		
		sudokuBoard.repaint();
		
	}

	private void reDraw() {
		sudokuBoard.paint(sudokuBoard.getGraphics());
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		sudokuBoard.repaint();
	}
	
	private void reduceSets(Cell[][] result, int m, int n) {
		if (result[m][n].getPossibleNumbers().size() == 1
				&& result[m][n].getNumber() == null) {
			Iterator<Integer> iter = result[m][n].getPossibleNumbers()
					.iterator();
			result[m][n].setNumber(iter.next());
			reDraw();
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
	
}
