package com.demo.sudoku;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.demo.sudoku.model.Sudoku;
import com.demo.sudoku.model.SudokuFull;
import com.demo.sudoku.model.SudokuFullResult;
import com.demo.sudoku.model.SudokuTemplate;
import com.demo.sudoku.view.SudokuFrameFx;

public class SudokuStartFx extends Application {
	
	private static final String TITLE = "Suduko";

	@Override
	public void start(Stage primaryStage) throws Exception {
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
		
		SudokuFrameFx frame = new SudokuFrameFx(template);
		
		Scene scene = new Scene(frame, 300, 250);
		
		primaryStage.setTitle(TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
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
