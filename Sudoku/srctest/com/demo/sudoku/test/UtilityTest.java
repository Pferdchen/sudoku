package com.demo.sudoku.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.demo.sudoku.Utility;

public class UtilityTest {
	private final static int[][] template = { { 5, 3, 0, 0, 7, 0, 0, 0, 0 },
			{ 6, 0, 0, 1, 9, 5, 0, 0, 0 }, { 0, 9, 8, 0, 0, 0, 0, 6, 0 },
			{ 8, 0, 0, 0, 6, 0, 0, 0, 3 }, { 4, 0, 0, 8, 0, 3, 0, 0, 1 },
			{ 7, 0, 0, 0, 2, 0, 0, 0, 6 }, { 0, 6, 0, 0, 0, 0, 2, 8, 0 },
			{ 0, 0, 0, 4, 1, 9, 0, 0, 5 }, { 0, 0, 0, 0, 8, 0, 0, 7, 9 } };
	private final static int[][] result = { { 5, 3, 4, 6, 7, 8, 9, 1, 2 },
			{ 6, 7, 2, 1, 9, 5, 3, 4, 8 }, { 1, 9, 8, 3, 4, 2, 5, 6, 7 },
			{ 8, 5, 9, 7, 6, 1, 4, 2, 3 }, { 4, 2, 6, 8, 5, 3, 7, 9, 1 },
			{ 7, 1, 3, 9, 2, 4, 8, 5, 6 }, { 9, 6, 1, 5, 3, 7, 2, 8, 4 },
			{ 2, 8, 7, 4, 1, 9, 6, 3, 5 }, { 3, 4, 5, 2, 8, 6, 1, 7, 9 } };

	@Test
	public void testSum() {
		assertEquals(0, Utility.sum(-1));
		assertEquals(0, Utility.sum(0));
		assertEquals(1 + 2 + 3, Utility.sum(3));
	}

	@Test
	public void testFac() {
		assertEquals(0, Utility.fac(-1));
		assertEquals(0, Utility.fac(0));
		assertEquals(1 * 2 * 3, Utility.fac(3));
	}

	@Test
	public void testIsResolved() {
		assertFalse(Utility.isResolved(template));
		assertTrue(Utility.isResolved(result));
	}

	@Test
	public void testIsCorrect() {
		assertFalse(Utility.isCorrect(template));
		assertTrue(Utility.isCorrect(result));
	}

	@Test
	public void testIsCorrect2() {
		assertFalse(Utility.isCorrect2(template));
		assertTrue(Utility.isCorrect2(result));
	}

	@Test
	public void testNormalize() {

	}

	@Test
	public void testInitialize() {

	}
}
