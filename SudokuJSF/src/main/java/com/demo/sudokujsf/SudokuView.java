package com.demo.sudokujsf;

import com.demo.sudoku2.core.Sudoku;
import static com.demo.sudoku2.util.Normalizer.normalize;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class SudokuView implements Serializable {

    private Sudoku sudoku;
    
    Integer[] puzzle;

    @PostConstruct
    public void init() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("puzzles/puzzle1.txt").getFile());
        try {
            puzzle = normalize(file);
        } catch (IOException ex) {
            Logger.getLogger(SudokuView.class.getName()).log(Level.SEVERE, null, ex);
        }
        sudoku = new Sudoku(puzzle);
    }

    public void calculate() {
        sudoku.calculate();
    }

    public Sudoku getSudoku() {
        return sudoku;
    }

    public Integer[] getPuzzle() {
        return puzzle;
    }

}
