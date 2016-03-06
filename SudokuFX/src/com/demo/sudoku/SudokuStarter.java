package com.demo.sudoku;

import java.io.IOException;

import com.demo.sudoku.model.SudokuContainer;
import com.demo.sudoku.view.SudokuOverviewController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SudokuStarter extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;

	private final static String templatePathInString = "./resources/template1.dat";
	private SudokuContainer sContainer;

	/**
	 * Constructor
	 */
	public SudokuStarter() {
		// Load template sudoku in container
		sContainer = new SudokuContainer(templatePathInString, true);
	}

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Sudoku");

		initRootLayout();

		showSudokuOverview();
	}

	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			// Load root layout from fxml file.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(SudokuStarter.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			// Show the scene containing the root layout.
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shows the sudoku overview inside the root layout.
	 */
	public void showSudokuOverview() {
		try {
			// Load person overview.
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(SudokuStarter.class.getResource("view/SudokuOverview.fxml"));
			AnchorPane sudokuOverview = (AnchorPane) loader.load();

			// Set person overview into the center of root layout.
			rootLayout.setCenter(sudokuOverview);

			// Set sudoku into the cotroller
			SudokuOverviewController controller = loader.getController();
			controller.setSudokuInGrid(sContainer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the main stage.
	 *
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
