package com.demo.sudoku2.core;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Cell {

    private static final Set<Integer> FULL_SUGGESTIONS
            = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

    Integer result;

    Set<Integer> suggestions;

    /**
     * Constructs an empty cell with full suggestions.
     */
    public Cell() {
        this(null);
    }

    /**
     * Constructs a cell with given result and initializes suggestions. If the
     * result is null, suggestions is a set of numbers. If the result is an
     * unique number, suggestions is {result}.
     *
     * @param result
     */
    public Cell(Integer result) {
        this.result = result;
        if (result == null) {
            this.suggestions = FULL_SUGGESTIONS.stream()
                    .collect(Collectors.toSet());
        } else {
            if (result < 1 || result > 9) {
                throw new IllegalArgumentException(result
                        + " is invalid, it should between 1 and 9!");
            }
            this.suggestions = Set.of(result);
        }
    }

    public boolean isSolved() {
        return this.result == null;
    }

    public void removeOneSuggestion(Integer suggestion) {
        if (!isSolved()) {
            return;
        }
        if (suggestions.contains(suggestion)) {
            suggestions.remove(suggestion);
        }
        if (suggestions.size() == 1) {
            result = suggestions.iterator().next();
        }
    }

    @Override
    public String toString() {
        return result == null ? null : result.toString();
    }
/*
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.result);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Cell)) {
            return false;
        }
        Cell other = (Cell) obj;
        return Objects.equals(this.result, other.result);
    }
*/
}
