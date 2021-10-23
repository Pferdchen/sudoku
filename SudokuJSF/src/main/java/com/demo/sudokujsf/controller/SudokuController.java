package com.demo.sudokujsf.controller;

import com.demo.sudokujsf.data.PuzzleDao;
import com.demo.sudokujsf.entity.Puzzle;
import java.util.List;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class SudokuController {

    @Inject
    private PuzzleDao puzzleDao;

    private List<Puzzle> puzzles;

    // @Named provides access the return value via the EL variable name "puzzles" in the UI (e.g.,
    // Facelets or JSP view)
    @Produces
    @Named
    public List<Puzzle> getPuzzles() {
        return puzzles;
    }

    @PostConstruct
    public void initPuzzles() {
        puzzles = puzzleDao.getAllPuzzles();

    }

}
