package com.demo.sudoku3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * Algorithm
 *
 * @see https://www.cnblogs.com/grenet/p/3138654.html
 */
public class Sudoku {

    /**
     * States of all 81 cells of Sudoku
     */
    private final int[] _num = new int[81];
    /**
     * 7 corresponds to _v[6]=001000000=64
     */
    private final int[] _v = new int[10];
    private StringBuilder _s;
    private boolean _hasString;

    public Sudoku() {
        this(true);
    }

    public Sudoku(boolean hasString) {
        _v[0] = 1;
        for (int i = 1; i <= 8; i++) {
            _v[i] = _v[i - 1] * 2;
        }
        _v[9] = 511;

        for (int i = 0; i < 81; i++) {
            _num[i] = _v[9];
        }

        _s = new StringBuilder();
        _hasString = hasString;
    }

    /**
     * Gets the count of 1s. Example: (1, 1)=011000000, it meas the cell
     * contains 2 possible numbers, 7 and 8, get1Count(011000000)=2.
     *
     * @param cellNum its binary form corresponds possibilities of numbers in a
     * cell
     * @return the count of 1s
     */
    private int get1Count(int cellNum) {
        int c = 0;
        while (cellNum > 0) {
            cellNum = cellNum & (cellNum - 1);
            c += 1;
        }
        return c;
    }

    /**
     *
     * @param row
     * @param col
     * @param onesComplement ones' complement of removed number
     * @return cell state, that does not contain the removed number. 0 means
     * unsolved cell.
     */
    private int removeNum(int row, int col, int onesComplement) {
        int index = row * 9 + col;
        if (_num[index] > 0) {
            _num[index] = _num[index] & onesComplement;
        }
        return _num[index];
    }

    public boolean setNum(int row, int col, int num) {
        return setNumPri(row - 1, col - 1, num - 1);
    }

    public boolean setLine(int row, int[] num) {
        if (num.length == 0) {
            return true;
        }

        int len = num.length > 9 ? 9 : num.length;
        for (int i = 0; i < len; i++) {
            if ((num[i] > 0) && !(setNumPri(row - 1, i, num[i] - 1))) {
                return false;
            }
        }

        return true;
    }

    /**
     *
     * @param row
     * @param col
     * @param indexOfV index of _v. _v[6]=001000000 corresponds to 7.
     * @return
     */
    private boolean setNumPri(int row, int col, int indexOfV) {
        if ((_v[indexOfV] & _num[row * 9 + col]) == 0) {
            return false;
        }
        _num[row * 9 + col] = -(indexOfV + 1);// set result with negative number
        int onesComplement = _v[9] - _v[indexOfV];

        for (int i = 0; i < 9; i++) {
            if (removeNum(i, col, onesComplement) == 0) {
                return false;
            }
            if (removeNum(row, i, onesComplement) == 0) {
                return false;
            }
        }

        int r1 = (row / 3) * 3;
        int c1 = (col / 3) * 3;

        for (int i = r1; i <= r1 + 2; i++) {
            for (int j = c1; j <= c1 + 2; j++) {
                if (removeNum(i, j, onesComplement) == 0) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     *
     * @param index index of cell
     * @param num2 binary form corresponds to a number
     * @return
     */
    private boolean setNumPri(int index, int num2) {
        int row = index / 9;
        int col = index % 9;
        int i = 0;
        while (i < 9) {
            if (_v[i] == num2) {
                break;
            }
            i++;
        }
        return setNumPri(row, col, i);
    }

    /**
     * Finds the index of blank cell, which contains minimal possibilities of
     * numbers. The possibilities of numbers are at least 2.
     *
     * @return -1, -2, 0-80
     */
    private int findMinCell() {
        int i = 0;
        int c;
        int tP = -1;
        int tMin = 20;

        do {
            if (_num[i] > 0) {
                c = get1Count(_num[i]);
                if (c == 1) {
                    if (!setNumPri(i, _num[i])) {
                        return -2;
                    }

                    appendString("SetNum " + indexToXY(i));

                    if (i == tP) {
                        tP = -1;
                        tMin = 20;
                    }

                    i = -1;
                } else {
                    if (c < tMin) {
                        tP = i;
                        tMin = c;
                    }
                }
            }
            i += 1;
        } while (i < 81);

        return tP;
    }

    public int[] calculate() {
        Stack<List<Integer>> q = new Stack<>();
        List<Integer> l;

        _s = new StringBuilder();
        appendString("Init Matrix");

        int i;
        int k = findMinCell();

        while (k != -1) {
            if (k == -2) {
                if (q.isEmpty()) {
                    appendString("Error!!!!!", false);
                    return null;
                }

                l = q.pop();

                k = l.get(82);
                l.remove(82);

                i = l.get(81) + 1;
                l.remove(81);

                appendString("Stack Pop " + (q.size() + 1), false);
                restoreNum(l);

                k = findNextK(q, l, k, i);
            } else {
                l = new ArrayList<>();
                l.addAll(Arrays.stream(_num).boxed().collect(Collectors.toList()));

                k = findNextK(q, l, k, 1);
            }
        }

        appendString("Calculating Complete!!!!");

        int[] v = new int[81];
        for (i = 0; i < 81; i++) {
            v[i] = -_num[i];
        }
        return v;
    }

    private void restoreNum(List<Integer> l) {
        for (int i = 0; i < 81; i++) {
            _num[i] = l.get(i);
        }
        appendString("Restore Matrix");
    }

    /**
     * Gets the possible number with the index of possibilities in a cell.
     * Examples: (1, 1)=011000000, it meas the cell contains 2 possible numbers,
     * 7 and 8. getPossibleNumInCell(011000000, 1)=7 means the 1st possible
     * number is 7; getPossibleNumInCell(011000000, 2)=8 means the 2nd possible
     * number is 8; getPossibleNumInCell(011000000, 3)=-1 means there is no 3rd
     * possible number.
     *
     * @param cellNum its binary form corresponds possibilities of numbers in a
     * cell
     * @param indexOfPossibilities starts from 1, if there are possibilities
     * @return the possible number in the cell according to the given index of
     * possibilities
     */
    private int getPossibleNumInCell(int cellNum, int indexOfPossibilities) {
        int k = 0;
        for (int i = 0; i < 9; i++) {
            if ((_v[i] & cellNum) != 0) {
                k += 1;
                if (k == indexOfPossibilities) {
                    return i + 1;
                }
            }
        }
        return -1;
    }

    private int findNextK(Stack<List<Integer>> q, List<Integer> l, int k, int index) {
        int j = getPossibleNumInCell(_num[k], index);

        while (j != -1) {
            if (setNumPri(k, _v[j - 1])) {
                appendString("Stack Push " + (q.size() + 1), false);
                appendString("SetNum MayBe " + indexToXY(k));

                l.add(index);//????
                l.add(k);//????
                q.push(l);

                k = findMinCell();

                break;
            }

            restoreNum(l);
            index += 1;
            j = getPossibleNumInCell(_num[k], index);
        }
        if (j == -1) {
            k = -2;
        }
        return k;
    }

    private String returnNumString(int num) {
        if (num < 0) {
            return "#" + (-num) + " ";
        }
        String s = "";
        for (int i = 0; i < 9; i++) {
            if ((_v[i] & num) != 0) {
                s = s + (i + 1);
            }
        }
        return String.format("%-10s", s);
    }

    private String returnMatrix() {
        String s = "";
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                s = s + returnNumString(_num[i * 9 + j]);
            }
            s = s + "\n";
        }
        return s;
    }

    private void appendString(String text, boolean appendMatrix) {
        if (!_hasString) {
            return;
        }
        _s.append(text);
        _s.append("\n");
        if (appendMatrix) {
            _s.append(returnMatrix());
            _s.append("\n");
        }
    }

    private void appendString(String text) {
        appendString(text, true);
    }

    private String indexToXY(int index) {
        return ((index / 9) + 1) + "-" + ((index % 9) + 1) + "Num: " + (-_num[index]);
    }

    public String calculationString() {
        return _s.toString();
    }

    public static void main(String[] args) {
        resolve();
        //resolve2();
    }

    private static void resolve() {
        Sudoku tS = new Sudoku();
        tS.setLine(1, new int[]{0, 6, 0, 5, 9, 3, 0, 0, 0});
        tS.setLine(2, new int[]{9, 0, 1, 0, 0, 0, 5, 0, 0});
        tS.setLine(3, new int[]{0, 3, 0, 4, 0, 0, 0, 9, 0});
        tS.setLine(4, new int[]{1, 0, 8, 0, 2, 0, 0, 0, 4});
        tS.setLine(5, new int[]{4, 0, 0, 3, 0, 9, 0, 0, 1});
        tS.setLine(6, new int[]{2, 0, 0, 0, 1, 0, 6, 0, 9});
        tS.setLine(7, new int[]{0, 8, 0, 0, 0, 6, 0, 2, 0});
        tS.setLine(8, new int[]{0, 0, 4, 0, 0, 0, 8, 0, 7});
        tS.setLine(9, new int[]{0, 0, 0, 7, 8, 5, 0, 1, 0});

        tS.calculate();

        System.out.println(tS.calculationString());

        int[] result = {
            -7, -6, -2, -5, -9, -3, -1, -4, -8,
            -9, -4, -1, -2, -7, -8, -5, -3, -6,
            -8, -3, -5, -4, -6, -1, -7, -9, -2,
            -1, -9, -8, -6, -2, -7, -3, -5, -4,
            -4, -7, -6, -3, -5, -9, -2, -8, -1,
            -2, -5, -3, -8, -1, -4, -6, -7, -9,
            -3, -8, -7, -1, -4, -6, -9, -2, -5,
            -5, -1, -4, -9, -3, -2, -8, -6, -7,
            -6, -2, -9, -7, -8, -5, -4, -1, -3};

        System.out.println("Result " + (Arrays.equals(tS._num, result) ? "right" : "wrong"));
    }

    private static void resolve2() {
        Sudoku tS2 = new Sudoku();
        tS2.setLine(1, new int[]{0, 0, 0, 5, 0, 0, 0, 0, 0});
        tS2.setLine(2, new int[]{5, 2, 8, 3, 0, 0, 0, 9, 0});
        tS2.setLine(3, new int[]{0, 0, 4, 0, 0, 7, 0, 0, 5});
        tS2.setLine(4, new int[]{0, 0, 0, 9, 0, 3, 4, 2, 0});
        tS2.setLine(5, new int[]{6, 3, 2, 0, 0, 0, 0, 0, 0});
        tS2.setLine(6, new int[]{4, 8, 0, 0, 1, 2, 0, 0, 0});
        tS2.setLine(7, new int[]{2, 0, 3, 8, 0, 9, 0, 0, 1});
        tS2.setLine(8, new int[]{0, 7, 0, 0, 0, 0, 8, 0, 0});
        tS2.setLine(9, new int[]{8, 4, 0, 0, 0, 6, 5, 0, 0});

        tS2.calculate();

        System.out.println(tS2.calculationString());

        int[] result2 = {
            -1, -9, -7, -5, -2, -8, -3, -6, -4,
            -5, -2, -8, -3, -6, -4, -1, -9, -7,
            -3, -6, -4, -1, -9, -7, -2, -8, -5,
            -7, -1, -5, -9, -8, -3, -4, -2, -6,
            -6, -3, -2, -7, -4, -5, -9, -1, -8,
            -4, -8, -9, -6, -1, -2, -7, -5, -3,
            -2, -5, -3, -8, -7, -9, -6, -4, -1,
            -9, -7, -6, -4, -5, -1, -8, -3, -2,
            -8, -4, -1, -2, -3, -6, -5, -7, -9};

        System.out.println("Result " + (Arrays.equals(tS2._num, result2) ? "right" : "wrong"));
    }
}
