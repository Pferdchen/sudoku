package com.demo.sudokufx2;

import com.demo.sudoku2.core.Sudoku;
import com.demo.sudoku2.util.Normalizer;
import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Sudoku");
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("templates/template1.txt").getFile());
        Integer[] puzzle = Normalizer.normalize(file);
        Sudoku sudoku = new Sudoku(puzzle);
        System.out.println(sudoku.toString());
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
