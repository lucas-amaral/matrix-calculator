package com.antunesamaral.matrixcalculator.utils;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ListUtilsTest {

    @Test
    void convertArrayToBigIntegerList() {
        String[] values = new String[3];
        values[0] = "1";
        values[1] = "465456465";
        values[2] = "-123";

        assertThat(ListUtils.convertArrayToBigIntegerList(values))
                .asList()
                .containsOnly(new BigInteger("1"), new BigInteger("465456465"), new BigInteger("-123"));
    }

    @Test
    void convertArrayToBigIntegerList_shouldThrowNumberFormatExceptionIfThereInNonIntegerValue() {
        String[] values = new String[3];
        values[0] = "1";
        values[1] = "13s";
        values[2] = "-123";

        assertThrows(NumberFormatException.class, () -> ListUtils.convertArrayToBigIntegerList(values));
    }

    @Test
    void joiningElementsByComma() {
        List<List<BigInteger>> values = Collections.singletonList(Arrays.asList(
                new BigInteger("1"),
                new BigInteger("465456465"),
                new BigInteger("-123")
        ));

        assertThat(values.stream().map(ListUtils.joiningElementsByComma()))
                .asList()
                .containsOnly("1,465456465,-123");
    }

    @Test
    void sumElements() {
        List<List<BigInteger>> values = Collections.singletonList(Arrays.asList(
                new BigInteger("1"),
                new BigInteger("465456465"),
                new BigInteger("-123")
        ));

        assertThat(values.stream().map(ListUtils.sumElements()))
                .asList()
                .containsOnly(new BigInteger("465456343"));
    }

    @Test
    void multiplyElements() {
        List<List<BigInteger>> values = Collections.singletonList(Arrays.asList(
                new BigInteger("1"),
                new BigInteger("465456465"),
                new BigInteger("-123")
        ));

        assertThat(values.stream().map(ListUtils.multiplyElements()))
                .asList()
                .containsOnly(new BigInteger("-57251145195"));
    }
}