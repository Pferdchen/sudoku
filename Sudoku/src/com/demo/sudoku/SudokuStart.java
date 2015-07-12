package com.demo.sudoku;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import com.demo.sudoku.model.Sudoku;
import com.demo.sudoku.model.SudokuFull;
import com.demo.sudoku.model.SudokuFullResult;
import com.demo.sudoku.model.SudokuTemplate;
import com.demo.sudoku.view.SudokuFrame;

public class SudokuStart {

	public static void main(String[] args) {
		Path templatePath = FileSystems.getDefault().getPath(
				templatePathInString);
		Sudoku template = new SudokuTemplate(templatePath);
		System.out.println("-- <Template> --");
		printSudoku(template.getSudoku());

		System.out.println("--------------");
		System.out.println("Result direkt of template is "
				+ (Utility.isCorrect2(template.getSudoku()) ? "correct" : "not correct"));

		SudokuFull r = new SudokuFullResult(template.getSudoku());
		int[][] result = Utility.normalize(r.getSudoku());
		System.out.println("-- <Result> --");
		printSudoku(result);

		System.out.println("--------------");
		System.out.println("Result is "
				+ (Utility.isCorrect2(result) ? "correct" : "not correct"));

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				SudokuFrame frame = new SudokuFrame(template);
				frame.setVisible(true);
			}
		});

	}

	private static void printSudoku(int[][] sudoku) {
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku[i].length; j++)
				System.out.print(sudoku[i][j]);
			System.out.println();
		}
	}

	private final static String templatePathInString = "./templates/template1.txt";
}
