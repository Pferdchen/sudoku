package com.demo.sudoku2.core;

import java.util.Set;
import java.util.stream.Collectors;

public class Cell {

    private static final Set<Integer> FULL_SUGGESTIONS
            = Set.of(1, 2, 3, 4, 5, 6, 7, 8, 9);

    Integer result;

    Set<Integer> suggestions;

    /**
     * If the result is null, suggestions is a set of numbers. If the result is
     * an unique number, suggestions is {result}.
     *
     * @param result
     */
    public Cell(Integer result) {
        this.result = result;
        if (result == null) {
            this.suggestions = FULL_SUGGESTIONS.stream()
                    .collect(Collectors.toSet());
        } else {
            this.suggestions = Set.of(result);
        }
    }

    @Override
    public String toString() {
        return result == null ? null : result.toString();
    }

}
