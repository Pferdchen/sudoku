package com.demo.sudoku.view;

import com.demo.sudoku.model.Sudoku;
import com.demo.sudoku.model.SudokuContainer;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class SudokuOverviewController {
	@FXML
	private GridPane sudokuGrid;

	/**
	 * The constructor. The constructor is called before the initialize()
	 * method.
	 */
	public SudokuOverviewController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// TODO paint grid lines
	}

	/**
	 * Set sudoku data from container into GridPane.
	 *
	 * @param sContainer
	 */
	public void setSudokuInGrid(SudokuContainer sContainer) {
		int[][] sudoku = sContainer.getSudoku();
		for (int i = 0; i < Sudoku.SIZE; i++) {
			for (int j = 0; j < Sudoku.SIZE; j++) {
				String text = sudoku[i][j] != 0 ? Integer.toString(sudoku[i][j]) : "";
				Node node = new Label(text);
				sudokuGrid.add(node, j, i);
			}
		}
	}
}
