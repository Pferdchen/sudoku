package com.demo.sudoku.view;

import java.awt.Font;

import javafx.scene.Node;

import com.demo.sudoku.model.Cell;
import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;

public class SudokuBoardDisplayFx extends Node {
	
	// constants
	private static final int CELL_PIXELS = 50; // Size of each cell.
	private static final int PUZZLE_SIZE = 9; // Number of rows/cols
	private static final int SUBSQUARE = 3; // Size of subsquare.
	private static final int BOARD_PIXELS = CELL_PIXELS * PUZZLE_SIZE;
	private static final int TEXT_OFFSET = 15; // Fine tuning placement of text.
	private static final Font TEXT_FONT = new Font("Sansserif", Font.BOLD, 24);

	// fields
	// private Sudoku _model; // Set in constructor.
	private Cell[][] result;// TODO should use a class with Setter and Getter
	
	
	// constructor
	public SudokuBoardDisplayFx(Cell[][] result) {
		this.result = result;
		
	}
	
	
	@Override
	protected boolean impl_computeContains(double arg0, double arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BaseBounds impl_computeGeomBounds(BaseBounds arg0, BaseTransform arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected NGNode impl_createPeer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object impl_processMXNode(MXNodeAlgorithm arg0,
			MXNodeAlgorithmContext arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
