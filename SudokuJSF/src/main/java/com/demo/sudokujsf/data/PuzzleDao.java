package com.demo.sudokujsf.data;

import com.demo.sudokujsf.entity.Puzzle;
import java.util.List;

public interface PuzzleDao {

    List<Puzzle> getAllPuzzles();
}
