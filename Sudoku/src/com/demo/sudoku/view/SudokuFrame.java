package com.demo.sudoku.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.demo.sudoku.Utility;
import com.demo.sudoku.controller.ResolveListener;
import com.demo.sudoku.model.Cell;
import com.demo.sudoku.model.Sudoku;

public class SudokuFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 0;

	private static final String TITLE = "Suduko";

	private int defaultDelay = 1;

	private SudokuBoardDisplay sudokuBoard;

	private Cell[][] result;

	public SudokuFrame(Sudoku template) {// TODO template should be load here...

		// 1... Create/initialize components
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());

		controlPanel.add(new JLabel("Delay:"));
		JTextField delayField = new JTextField(2);
		delayField.setText(Integer.toString(defaultDelay));
		controlPanel.add(delayField);

		JButton runBtn = new JButton("Run");
		controlPanel.add(runBtn);

		// 2... Create content panel, set layout
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());

		// 3... Add the components to the content panel.
		content.add(controlPanel, BorderLayout.NORTH);
		result = Utility.initialize(template.getSudoku());
		sudokuBoard = new SudokuBoardDisplay(result);
		content.add(sudokuBoard, BorderLayout.CENTER);

		// ... Add listener
		runBtn.addActionListener(new ResolveListener(sudokuBoard, result));

		// 4... Set this window's attributes, and pack it.
		setContentPane(content);
		setTitle(TITLE);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false); // Don't let user resize it.
		pack();
		setLocationRelativeTo(null); // Center it.
	}

}
