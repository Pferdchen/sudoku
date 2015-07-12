package com.demo.sudoku.model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SudokuTemplate implements Sudoku {

	private int[][] template;

	public SudokuTemplate() {
		this.template = new int[SIZE][SIZE];
	}

	public SudokuTemplate(Path templatePath) {
		try {
			List<String> lines = new ArrayList<String>();
			for (String line : Files.readAllLines(templatePath))
				lines.add(line);

			boolean isSudoku = true;
			if (lines.size() != SIZE) {
				isSudoku = false;
			} else {
				for (int i = 0; i < SIZE; i++) {
					if (lines.get(i).length() != SIZE) {
						isSudoku = false;
						break;
					}
				}
			}

			if (isSudoku)
				setTemplate(lines);
			else {
				this.template = null;
			}
		} catch (IOException e) {
			System.out
					.println("An I/O error occurs reading from the file or a malformed or unmappable byte sequence is read");
		}
	}

	public SudokuTemplate(String templatePath) {
		try (FileInputStream fin = new FileInputStream(templatePath);
				BufferedReader br = new BufferedReader(new InputStreamReader(
						fin));) {
			boolean isSudoku = true;
			List<String> lines = new ArrayList<String>();
			while (br.ready()) {
				lines.add(br.readLine());
			}
			int row = lines.size();
			if (row == 0)
				isSudoku = false;

			int length = lines.get(0).length();
			for (int i = 1; i < row; i++) {
				if (lines.get(i).length() != length)
					isSudoku = false;
			}// '\n'=10,' '=32

			if (isSudoku)
				setTemplate(lines);
			else {
				this.template = null;
			}
		} catch (FileNotFoundException ex) {
			System.out.println("Input file is not found");
		} catch (IOException ex) {
			System.out.println("Can't read the file");
		}
	}

	private void setTemplate(List<String> lines) {
		int[][] template = new int[SIZE][SIZE];
		int i = 0;
		for (String line : lines) {
			for (int j = 0; j < SIZE; j++) {
				char ch = line.charAt(j);
				if (ch == ' ')
					template[i][j] = 0;
				else
					template[i][j] = Character.getNumericValue(ch);
			}
			i++;
		}
		this.template = template;
	}

	@Override
	public int[][] getSudoku() {
		return this.template;
	}

}
