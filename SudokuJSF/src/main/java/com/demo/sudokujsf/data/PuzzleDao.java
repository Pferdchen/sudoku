package com.demo.sudokujsf.data;

import com.demo.sudokujsf.model.Puzzle;
import java.util.List;

/**
 *
 * @author gao
 */
public interface PuzzleDao {

    List<Puzzle> getAllPuzzles();
}
