package com.demo.sudoku2.core;

import static com.demo.sudoku2.util.Indexer.columnIndex;
import static com.demo.sudoku2.util.Indexer.regionIndex;
import static com.demo.sudoku2.util.Indexer.rowIndex;
import java.util.ArrayList;
import java.util.List;
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
        if (puzzle == null || puzzle.length != 81) {
            throw new IllegalArgumentException("The given puzzle is not valid!");
        }
        this.puzzle = puzzle;
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
            if (cell.result == null) {
                Set<Integer> possibleValues = cell.suggestions;

                int rowIdx = rowIndex(i);
                rows.get(rowIdx).stream()
                        .filter(c -> (c != cell && c.result != null))
                        .forEach(c -> {
                            possibleValues.remove(c.result);// remove in row
                        });

                int columnIdx = columnIndex(i);
                columns.get(columnIdx).stream()
                        .filter(c -> (c != cell && c.result != null))
                        .forEach(c -> {
                            possibleValues.remove(c.result);// remove in column
                        });

                int regionIdx = regionIndex(i);
                regions.get(regionIdx).stream()
                        .filter(c -> (c != cell && c.result != null))
                        .forEach(c -> {
                            possibleValues.remove(c.result);// remove in region
                        });

                reduceSets(i);
            }
        }
    }

    private void reduceSets(int i) {
        Cell cell = solution.get(i);
        Set<Integer> possibleValues = cell.suggestions;
        if (possibleValues.size() == 1 && cell.result == null) {
            cell.result = possibleValues.iterator().next();
            reduceSetsInRows(i);
            reduceSetsInColumns(i);
            reduceSetsInRegion(i);
        }
    }

    private void reduceSetsInRows(int i) {
        Cell cell = solution.get(i);
        int rowIdx = rowIndex(i);
        rows.get(rowIdx).stream()
                .filter(c -> (c != cell && c.suggestions.size() > 1))
                .forEach(c -> {
                    c.suggestions.remove(cell.result);// reduce sets in row
                    reduceSets(solution.indexOf(c));
                });
    }

    private void reduceSetsInColumns(int i) {
        Cell cell = solution.get(i);
        int columnIdx = columnIndex(i);
        columns.get(columnIdx).stream()
                .filter(c -> (c != cell && c.suggestions.size() > 1))
                .forEach(c -> {
                    c.suggestions.remove(cell.result);// reduce sets in column
                    reduceSets(solution.indexOf(c));
                });
    }

    private void reduceSetsInRegion(int i) {
        Cell cell = solution.get(i);
        int regionIdx = regionIndex(i);
        regions.get(regionIdx).stream()
                .filter(c -> (c != cell && c.suggestions.size() > 1))
                .forEach(c -> {
                    c.suggestions.remove(cell.result);// reduce sets in region
                    reduceSets(solution.indexOf(c));
                });
    }

    @Override
    public String toString() {
        return solution.toString();
    }

}
