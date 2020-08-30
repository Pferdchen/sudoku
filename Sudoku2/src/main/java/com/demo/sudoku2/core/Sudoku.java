package com.demo.sudoku2.core;

import static com.demo.sudoku2.util.Indexer.columnIndex;
import static com.demo.sudoku2.util.Indexer.regionIndex;
import static com.demo.sudoku2.util.Indexer.rowIndex;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

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
     * Constructs a new Sudoku with the given puzzle.
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
            cell.index = i;
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
        solution.stream().filter(unsolvedCellPredicate()).map(unsolvedCell -> {
            reduceUnsolvedCellInRow(unsolvedCell);
            reduceUnsolvedCellInColumn(unsolvedCell);
            reduceUnsolvedCellInRegion(unsolvedCell);
            return unsolvedCell;
        }).filter(solvedCellPredicate()).forEachOrdered(solvedCell -> {
            reduceOtherUnsolvedCells(solvedCell);
        });
    }

    private void reduceUnsolvedCellInRow(Cell unsolvedCell) {
        int rowIdx = rowIndex(unsolvedCell.index);
        rows.get(rowIdx).stream().filter(solvedCellPredicate())
                .forEach(reduceUnsolvedCellConsumer(unsolvedCell));
    }

    private void reduceUnsolvedCellInColumn(Cell unsolvedCell) {
        int columnIdx = columnIndex(unsolvedCell.index);
        columns.get(columnIdx).stream().filter(solvedCellPredicate())
                .forEach(reduceUnsolvedCellConsumer(unsolvedCell));
    }

    private void reduceUnsolvedCellInRegion(Cell unsolvedCell) {
        int regionIdx = regionIndex(unsolvedCell.index);
        regions.get(regionIdx).stream().filter(solvedCellPredicate())
                .forEach(reduceUnsolvedCellConsumer(unsolvedCell));
    }

    private static Predicate<Cell> solvedCellPredicate() {
        return c -> c.isSolved();
    }

    private static Consumer<Cell> reduceUnsolvedCellConsumer(Cell unsolvedCell) {
        return solvedCell -> {
            unsolvedCell.removeOneSuggestion(solvedCell.result);
        };
    }

    private void reduceOtherUnsolvedCells(Cell solvedCell) {
        reduceOtherUnsolvedCellsInSameRow(solvedCell);
        reduceOtherUnsolvedCellsInSameColumn(solvedCell);
        reduceOtherUnsolvedCellsInSameRegion(solvedCell);
    }

    private void reduceOtherUnsolvedCellsInSameRow(Cell solvedCell) {
        int rowIdx = rowIndex(solvedCell.index);
        rows.get(rowIdx).stream().filter(unsolvedCellPredicate())
                .forEach(reduceWithSolvedCellConsumer(solvedCell));
    }

    private void reduceOtherUnsolvedCellsInSameColumn(Cell solvedCell) {
        int columnIdx = columnIndex(solvedCell.index);
        columns.get(columnIdx).stream().filter(unsolvedCellPredicate())
                .forEach(reduceWithSolvedCellConsumer(solvedCell));
    }

    private void reduceOtherUnsolvedCellsInSameRegion(Cell solvedCell) {
        int regionIdx = regionIndex(solvedCell.index);
        regions.get(regionIdx).stream().filter(unsolvedCellPredicate())
                .forEach(reduceWithSolvedCellConsumer(solvedCell));
    }

    private static Predicate<Cell> unsolvedCellPredicate() {
        return c -> !c.isSolved();
    }

    /*Be careful, it's non-static method as reduceUnsolvedCellConsumer() is*/
    private Consumer<Cell> reduceWithSolvedCellConsumer(Cell solvedCell) {
        return unsolvedCell -> {
            unsolvedCell.removeOneSuggestion(solvedCell.result);
            if (unsolvedCell.isSolved()) {
                reduceOtherUnsolvedCells(unsolvedCell);
            }
        };
    }

    public boolean isSolved() {
        return solution.stream().noneMatch(cell -> (!cell.isSolved()));
    }

    @Override
    public String toString() {
        return solution.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.solution);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Sudoku)) {
            return false;
        }
        Sudoku other = (Sudoku) obj;
        return Objects.equals(this.solution, other.solution);
    }

}
