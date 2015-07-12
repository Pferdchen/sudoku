package com.demo.sudoku.model;

import java.util.HashSet;
import java.util.Set;

public class Cell {

	private Integer number;

	private Set<Integer> possibleNumbers;

	public Cell() {
		number = null;
		possibleNumbers = new HashSet<>();
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Set<Integer> getPossibleNumbers() {
		return possibleNumbers;
	}

	public void setPossibleNumbers(Set<Integer> possibleNumbers) {
		this.possibleNumbers = possibleNumbers;
	}

	@Override
	public String toString() {
		return this.number != null ? this.number.toString() : null;
	}
}
