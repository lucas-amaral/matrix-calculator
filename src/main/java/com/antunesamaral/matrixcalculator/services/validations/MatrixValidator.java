package com.antunesamaral.matrixcalculator.services.validations;

import com.antunesamaral.matrixcalculator.entities.Matrix;

import java.math.BigInteger;
import java.util.List;

public class MatrixValidator {

    /**
     * Check if the number of rows are equal to the number of columns
     */
    public static boolean isSquare(Matrix matrix) {
        return matrix.getRows().parallelStream().allMatch(row -> row.size() == matrix.getRows().size());
    }

    /**
     * Check if the number of rows are equal to the number of columns
     */
    public static boolean isSquare(List<List<String>> matrix) {
        return matrix.parallelStream().allMatch(row -> row.size() == matrix.size());
    }

    /**
     * Check if all elements are integer
     */
    public static boolean hasOnlyIntegerNumbers(List<List<String>> matrix) {
        return matrix.stream()
                .flatMap(List::stream)
                .allMatch(MatrixValidator::isBigInteger);
    }

    static boolean isBigInteger(String strNum) {
        try {
            new BigInteger(strNum);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
