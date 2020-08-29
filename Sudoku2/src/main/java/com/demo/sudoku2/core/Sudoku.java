package com.demo.sudoku2.core;

import static com.demo.sudoku2.util.Indexer.columnIndex;
import static com.demo.sudoku2.util.Indexer.regionIndex;
import static com.demo.sudoku2.util.Indexer.rowIndex;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Sudoku {

    private final Integer[] puzzle;

    /**
     * 81 Cells
     */
    private final List<Cell> solution;

    /**
     * 9 rows, each has 9 cells
     */
    private final List<List<Cell>> rows;

    /**
     * 9 columns, each has 9 cells
     */
    private final List<List<Cell>> columns;

    /**
     * 9 regions, each has 9 cells
     */
    private final List<List<Cell>> regions;

    /**
     * Constructs a new sudoku with the given puzzle.
     *
     * @param puzzle is an array of 81 {@code Integer}s.
     */
    public Sudoku(Integer[] puzzle) {
        this.puzzle = Objects.requireNonNull(puzzle,
                "Input puzzle is an Integer array with 81 elements!");
        validateBefore();
        this.rows = new ArrayList<>();
        this.columns = new ArrayList<>();
        this.regions = new ArrayList<>();
        int i = 0;
        while (i < 9) {
            this.rows.add(new ArrayList<>());
            this.columns.add(new ArrayList<>());
            this.regions.add(new ArrayList<>());
            i++;
        }
        this.solution = new ArrayList<>();
        i = 0;
        while (i < 81) {
            Cell cell = new Cell(puzzle[i]);
            this.solution.add(cell);
            this.rows.get(rowIndex(i)).add(cell);
            this.columns.get(columnIndex(i)).add(cell);
            this.regions.get(regionIndex(i)).add(cell);
            i++;
        }
        validateAfter();
    }

    private void validateBefore() {
        if (this.puzzle == null || this.puzzle.length != 81) {
            throw new IllegalArgumentException(
                    "The length of given puzzle is invalid!");
        }
    }

    private void validateAfter() {
        int i = 1;
        while (i <= 9) {
            if (hasDuplicate(this.rows.get(i - 1))) {
                throw new IllegalArgumentException(
                        i + ". row of given puzzle is invalid!");
            }
            if (hasDuplicate(this.columns.get(i - 1))) {
                throw new IllegalArgumentException(
                        i + ". column of given puzzle is invalid!");
            }
            if (hasDuplicate(this.regions.get(i - 1))) {
                throw new IllegalArgumentException(
                        i + ". region of given puzzle is invalid!");
            }
            i++;
        }
    }

    private static boolean hasDuplicate(List<Cell> cells) {
        Set<Integer> intSet = new HashSet<>();
        for (Cell cell : cells) {
            if (cell.isSolved()) {
                if (intSet.contains(cell.result)) {
                    return true;
                } else {
                    intSet.add(cell.result);
                }
            }
        }
        return false;
    }

    public Integer[] getPuzzle() {
        return puzzle;
    }

    public List<Cell> getSolution() {
        return solution;
    }

    public void calculate() {
        for (int i = 0; i < 81; i++) {
            Cell cell = solution.get(i);
            // reduce suggestions for empty cell
            if (!cell.isSolved()) {
                // scan its row for reducing its suggestions with other not
                // empty cells in the row
                int rowIdx = rowIndex(i);
                rows.get(rowIdx).stream().filter(c -> c.isSolved())
                        .forEach(other -> {
                            cell.removeOneSuggestion(other.result);
                        });

                // scan its column for reducing its suggestions with other not
                // empty cells in the column
                int columnIdx = columnIndex(i);
                columns.get(columnIdx).stream().filter(c -> c.isSolved())
                        .forEach(other -> {
                            cell.removeOneSuggestion(other.result);
                        });

                // scan its region for reducing its suggestions with other not
                // empty cells in the region
                int regionIdx = regionIndex(i);
                regions.get(regionIdx).stream().filter(c -> c.isSolved())
                        .forEach(other -> {
                            cell.removeOneSuggestion(other.result);
                        });

                if (cell.isSolved()) {
                    reduceOtherSets(i);
                }
            }
        }
    }

    private void reduceOtherSets(int i) {
        reduceOtherSetsInRows(i);
        reduceOtherSetsInColumns(i);
        reduceOtherSetsInRegion(i);
    }

    private void reduceOtherSetsInRows(int i) {
        Cell cell = solution.get(i);
        int rowIdx = rowIndex(i);
        rows.get(rowIdx).stream().filter(c -> !c.isSolved())
                .forEach(other -> {
                    other.removeOneSuggestion(cell.result);
                    if (other.isSolved()) {
                        reduceOtherSets(solution.indexOf(other));
                    }
                });
    }

    private void reduceOtherSetsInColumns(int i) {
        Cell cell = solution.get(i);
        int columnIdx = columnIndex(i);
        columns.get(columnIdx).stream().filter(c -> !c.isSolved())
                .forEach(other -> {
                    other.removeOneSuggestion(cell.result);
                    if (other.isSolved()) {
                        reduceOtherSets(solution.indexOf(other));
                    }
                });
    }

    private void reduceOtherSetsInRegion(int i) {
        Cell cell = solution.get(i);
        int regionIdx = regionIndex(i);
        regions.get(regionIdx).stream().filter(c -> !c.isSolved())
                .forEach(other -> {
                    other.removeOneSuggestion(cell.result);
                    if (other.isSolved()) {
                        reduceOtherSets(solution.indexOf(other));
                    }
                });
    }

    public boolean isSolved() {
        return solution.stream().noneMatch(cell -> (!cell.isSolved()));
    }

    @Override
    public String toString() {
        return solution.toString();
    }

}
