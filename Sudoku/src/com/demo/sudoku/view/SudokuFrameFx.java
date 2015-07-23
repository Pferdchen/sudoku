package com.demo.sudoku.view;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import com.demo.sudoku.Utility;
import com.demo.sudoku.model.Cell;
import com.demo.sudoku.model.Sudoku;

public class SudokuFrameFx extends Parent {

	private int defaultDelay = 1;

	private SudokuBoardDisplayFx sudokuBoard;

	private Cell[][] result;
	
	public SudokuFrameFx(Sudoku template) {// TODO template should be load here...
		
		// 1... Create/initialize components
		FlowPane controlPanel = new FlowPane();
		TextField delayField = new TextField("Hello");//int 2
		delayField.setText(Integer.toString(defaultDelay));
		controlPanel.getChildren().add(delayField);
		
		Button runBtn = new Button("Run");
		controlPanel.getChildren().add(runBtn);
		
		// 2... Create content panel, set layout
		BorderPane content = new BorderPane();
		
		// 3... Add the components to the content panel.
		content.setTop(controlPanel);
		result = Utility.initialize(template.getSudoku());
//		sudokuBoard = new SudokuBoardDisplayFx(result);//NPE
		content.setCenter(sudokuBoard);
		
		// ... Add listener

	
		getChildren().add(content);
	}
}
