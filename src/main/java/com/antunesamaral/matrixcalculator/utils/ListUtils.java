package com.antunesamaral.matrixcalculator.utils;

import com.antunesamaral.matrixcalculator.entities.Delimiter;
import com.antunesamaral.matrixcalculator.exceptions.CalculationException;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ListUtils {

    /**
     * Convert from String array to a biginteger list
     * If there is any non biginteger value, will throw a NumberFormatException
     */
    public static List<BigInteger> convertArrayToBigIntegerList(String[] values) throws NumberFormatException {
        return Arrays.stream(values)
                .map(BigInteger::new)
                .collect(Collectors.toList());
    }

    /**
     * Creates a {@link String} with all list elements concatenated by comma
     */
    public static Function<List<BigInteger>, String> joiningElementsByComma() {
        return elements -> elements.stream()
                .map(Object::toString)
                .collect(Collectors.joining(Delimiter.COMMA.delimiter()));
    }

    /**
     * Creates a {@link BigInteger} that represents the sum of all list elements
     */
    public static Function<List<BigInteger>, BigInteger> sumElements() {
        return elements -> elements.parallelStream()
                .reduce(BigInteger::add)
                .orElseThrow(() -> new CalculationException("Error to sum: " + elements));
    }

    /**
     * Creates a {@link BigInteger} that represents the product of all list elements
     */
    public static Function<List<BigInteger>, BigInteger> multiplyElements() {
        return elements -> elements.parallelStream()
                .reduce(BigInteger::multiply)
                .orElseThrow(() -> new CalculationException("Error to multiply: " + elements));
    }
}
