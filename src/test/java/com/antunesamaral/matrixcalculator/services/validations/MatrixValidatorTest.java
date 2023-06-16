package com.antunesamaral.matrixcalculator.services.validations;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import com.antunesamaral.matrixcalculator.entities.Matrix;
import org.junit.jupiter.api.Test;

class MatrixValidatorTest {

    @Test
    void isSquare_shouldReturnsTrueForSquareMatrix() {
        List<BigInteger> row1 = Arrays.asList(new BigInteger("1"), new BigInteger("2"), new BigInteger("3"));
        List<BigInteger> row2 = Arrays.asList(new BigInteger("4"), new BigInteger("5"), new BigInteger("6"));
        List<BigInteger> row3 = Arrays.asList(new BigInteger("7"), new BigInteger("8"), new BigInteger("9"));
        Matrix matrix  = new Matrix(Arrays.asList(row1, row2, row3));

        assertThat(MatrixValidator.isSquare(matrix)).isTrue();
    }

    @Test
    void isSquare_shouldReturnsFalseWhenFirstRowHasLessElements() {
        List<BigInteger> row1 = Arrays.asList(new BigInteger("3"));
        List<BigInteger> row2 = Arrays.asList(new BigInteger("4"), new BigInteger("5"), new BigInteger("6"));
        List<BigInteger> row3 = Arrays.asList(new BigInteger("7"), new BigInteger("8"), new BigInteger("9"));
        Matrix matrix  = new Matrix(Arrays.asList(row1, row2, row3));

        assertThat(MatrixValidator.isSquare(matrix)).isFalse();
    }

    @Test
    void isSquare_shouldReturnsFalseWhenSecondRowHasLessElements() {
        List<BigInteger> row1 = Arrays.asList(new BigInteger("1"), new BigInteger("2"), new BigInteger("3"));
        List<BigInteger> row2 = Arrays.asList(new BigInteger("4"), new BigInteger("6"));
        List<BigInteger> row3 = Arrays.asList(new BigInteger("7"), new BigInteger("8"), new BigInteger("9"));
        Matrix matrix  = new Matrix(Arrays.asList(row1, row2, row3));

        assertThat(MatrixValidator.isSquare(matrix)).isFalse();
    }

    @Test
    void isSquare_shouldReturnsFalseWhenThirtyRowHasMoreElements() {
        List<BigInteger> row1 = Arrays.asList(new BigInteger("1"), new BigInteger("2"), new BigInteger("3"));
        List<BigInteger> row2 = Arrays.asList(new BigInteger("4"), new BigInteger("5"), new BigInteger("6"));
        List<BigInteger> row3 = Arrays.asList(new BigInteger("7"), new BigInteger("8"), new BigInteger("9"), new BigInteger("10"));
        Matrix matrix  = new Matrix(Arrays.asList(row1, row2, row3));

        assertThat(MatrixValidator.isSquare(matrix)).isFalse();
    }

    @Test
    void isSquare_shouldReturnsFalseWhenThereIsLessRowsThanElements() {
        List<BigInteger> row1 = Arrays.asList(new BigInteger("1"), new BigInteger("2"), new BigInteger("3"));
        List<BigInteger> row2 = Arrays.asList(new BigInteger("4"), new BigInteger("5"), new BigInteger("6"));
        Matrix matrix  = new Matrix(Arrays.asList(row1, row2));

        assertThat(MatrixValidator.isSquare(matrix)).isFalse();
    }

    @Test
    void hasOnlyIntegerNumbers_withOnlyIntegerNumbers() {
        List<String> row1 = Arrays.asList("1", "2", "3");
        List<String> row2 = Arrays.asList("4", "5", "6");
        List<String> row3 = Arrays.asList("7", "8", "9");

        assertThat(MatrixValidator.hasOnlyIntegerNumbers(Arrays.asList(row1, row2, row3))).isTrue();
    }

    @Test
    void hasOnlyIntegerNumbers_withDecimalNumbers() {
        List<String> row1 = Arrays.asList("1", "2", "3");
        List<String> row2 = Arrays.asList("4", "5.4", "6");
        List<String> row3 = Arrays.asList("7", "8", "9.2");

        assertThat(MatrixValidator.hasOnlyIntegerNumbers(Arrays.asList(row1, row2, row3))).isFalse();
    }

    @Test
    void hasOnlyIntegerNumbers_withStringValue() {
        List<String> row1 = Arrays.asList("1", "2", "3");
        List<String> row2 = Arrays.asList("4", "test", "6");
        List<String> row3 = Arrays.asList("7", "8", "9");

        assertThat(MatrixValidator.hasOnlyIntegerNumbers(Arrays.asList(row1, row2, row3))).isFalse();
    }

    @Test
    void hasOnlyIntegerNumbers_withNull() {
        List<String> row1 = Arrays.asList("1", "2", null);
        List<String> row2 = Arrays.asList("4", "5", "6");
        List<String> row3 = Arrays.asList("7", "8", "9");

        assertThat(MatrixValidator.hasOnlyIntegerNumbers(Arrays.asList(row1, row2, row3))).isFalse();
    }

    @Test
    void isInteger_withBigInteger() {
        assertThat(MatrixValidator.isBigInteger("423454225544554")).isTrue();
    }

    @Test
    void isInteger_withDecimalNumber() {
        assertThat(MatrixValidator.isBigInteger("43.5")).isFalse();
    }

    @Test
    void isInteger_withString() {
        assertThat(MatrixValidator.isBigInteger("t3sa")).isFalse();
    }

    @Test
    void isInteger_withNull() {
        assertThat(MatrixValidator.isBigInteger(null)).isFalse();
    }
}