package com.antunesamaral.matrixcalculator.services;

import com.antunesamaral.matrixcalculator.exceptions.CsvReadException;
import com.antunesamaral.matrixcalculator.exceptions.MatrixException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FileServiceTest {

    private FileService fileService;

    @BeforeEach
    void setUp() {
        fileService = new FileService();
    }

    @Test
    void readMatrix_shouldReturnsMatrixObjectForAValidCsv() {
        final String csv = "1,2,3\n" +
                "4,5,6\n" +
                "7,8,9";

        final MockMultipartFile file = new MockMultipartFile(
                "file",
                "matrix.csv",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                csv.getBytes()
        );

        List<BigInteger> row1 = Arrays.asList(new BigInteger("1"), new BigInteger("2"), new BigInteger("3"));
        List<BigInteger> row2 = Arrays.asList(new BigInteger("4"), new BigInteger("5"), new BigInteger("6"));
        List<BigInteger> row3 = Arrays.asList(new BigInteger("7"), new BigInteger("8"), new BigInteger("9"));

        assertThat(fileService.readMatrix(file))
                .isNotNull()
                .matches(matrix -> matrix.getRows().size() == 3)
                .matches(matrix -> matrix.getRows().get(0).equals(row1))
                .matches(matrix -> matrix.getRows().get(1).equals(row2))
                .matches(matrix -> matrix.getRows().get(2).equals(row3));
    }

    @Test
    void readMatrix_shouldThrowCsvExceptionIfSomeErrorHappen() throws IOException {
        final MockMultipartFile file = mock(MockMultipartFile.class);

        when(file.getInputStream()).thenThrow(new IOException("error"));

        assertThrows(CsvReadException.class, () -> fileService.readMatrix(file));
    }

    @Test
    void readMatrix_shouldThrowMatrixExceptionIfHasNonIntegerValue() {
        final String csv = "1,2,3\n" +
                "4,5,6\n" +
                "7,8,b";

        final MockMultipartFile file = new MockMultipartFile(
                "file",
                "matrix.csv",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                csv.getBytes()
        );

        assertThrows(MatrixException.class, () -> fileService.readMatrix(file));
    }

    @Test
    void readMatrix_shouldReturnsMatrixObjectForACsvUsingTabDelimiter() {
        final String csv = "1\t2\t3\n" +
                "4\t5\t6\n" +
                "7\t8\t9";

        final MockMultipartFile file = new MockMultipartFile(
                "file",
                "matrix.csv",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                csv.getBytes()
        );

        List<BigInteger> row1 = Arrays.asList(new BigInteger("1"), new BigInteger("2"), new BigInteger("3"));
        List<BigInteger> row2 = Arrays.asList(new BigInteger("4"), new BigInteger("5"), new BigInteger("6"));
        List<BigInteger> row3 = Arrays.asList(new BigInteger("7"), new BigInteger("8"), new BigInteger("9"));

        assertThat(fileService.readMatrix(file))
                .isNotNull()
                .matches(matrix -> matrix.getRows().size() == 3)
                .matches(matrix -> matrix.getRows().get(0).equals(row1))
                .matches(matrix -> matrix.getRows().get(1).equals(row2))
                .matches(matrix -> matrix.getRows().get(2).equals(row3));
    }

    @Test
    void readMatrix_shouldReturnsMatrixObjectForACsvUsingSemiColonDelimiter() {
        final String csv = "1;2;3\n" +
                "4;5;6\n" +
                "7;8;9";

        final MockMultipartFile file = new MockMultipartFile(
                "file",
                "matrix.csv",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                csv.getBytes()
        );

        List<BigInteger> row1 = Arrays.asList(new BigInteger("1"), new BigInteger("2"), new BigInteger("3"));
        List<BigInteger> row2 = Arrays.asList(new BigInteger("4"), new BigInteger("5"), new BigInteger("6"));
        List<BigInteger> row3 = Arrays.asList(new BigInteger("7"), new BigInteger("8"), new BigInteger("9"));

        assertThat(fileService.readMatrix(file))
                .isNotNull()
                .matches(matrix -> matrix.getRows().size() == 3)
                .matches(matrix -> matrix.getRows().get(0).equals(row1))
                .matches(matrix -> matrix.getRows().get(1).equals(row2))
                .matches(matrix -> matrix.getRows().get(2).equals(row3));
    }

    @Test
    void readMatrix_shouldReturnsMatrixObjectForACsvUsingSpaceDelimiter() {
        final String csv = "1 2 3\n" +
                "4 5 6\n" +
                "7 8 9";

        final MockMultipartFile file = new MockMultipartFile(
                "file",
                "matrix.csv",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                csv.getBytes()
        );

        List<BigInteger> row1 = Arrays.asList(new BigInteger("1"), new BigInteger("2"), new BigInteger("3"));
        List<BigInteger> row2 = Arrays.asList(new BigInteger("4"), new BigInteger("5"), new BigInteger("6"));
        List<BigInteger> row3 = Arrays.asList(new BigInteger("7"), new BigInteger("8"), new BigInteger("9"));

        assertThat(fileService.readMatrix(file))
                .isNotNull()
                .matches(matrix -> matrix.getRows().size() == 3)
                .matches(matrix -> matrix.getRows().get(0).equals(row1))
                .matches(matrix -> matrix.getRows().get(1).equals(row2))
                .matches(matrix -> matrix.getRows().get(2).equals(row3));
    }

    @Test
    void readMatrix_shouldThrowCsvReadExceptionForAnInvalidDelimiter() {
        final String csv = "1|2|3\n" +
                "4|5|6\n" +
                "7|8|9";

        final MockMultipartFile file = new MockMultipartFile(
                "file",
                "matrix.csv",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                csv.getBytes()
        );

        assertThrows(CsvReadException.class, () -> fileService.readMatrix(file));
    }

    @Test
    void readMatrix_shouldThrowCsvReadExceptionForAnInvalidFileExtension() {
        final String csv = "1,2,3\n" +
                "4,5,6\n" +
                "7,8,9";

        final MockMultipartFile file = new MockMultipartFile(
                "file",
                "matrix.xls",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                csv.getBytes()
        );

        assertThrows(CsvReadException.class, () -> fileService.readMatrix(file));
    }

    @Test
    void readMatrixAndInvert_shouldReturnsInvertedMatrixForAValidCsv() {
        final String csv = "1,2,3\n" +
                "4,5,6\n" +
                "7,8,9";

        final MockMultipartFile file = new MockMultipartFile(
                "file",
                "matrix.csv",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                csv.getBytes()
        );

        assertThat(fileService.readMatrixAndInvert(file))
                .isNotNull()
                .matches(matrix -> matrix.size() == 3)
                .matches(matrix -> matrix.get(0).equals(Arrays.asList("1", "4", "7")))
                .matches(matrix -> matrix.get(1).equals(Arrays.asList("2", "5", "8")))
                .matches(matrix -> matrix.get(2).equals(Arrays.asList("3", "6", "9")));
    }

    @Test
    void readMatrixAndInvert_shouldThrowCsvExceptionIfSomeErrorHappen() throws IOException {
        final MockMultipartFile file = mock(MockMultipartFile.class);

        when(file.getInputStream()).thenThrow(new IOException("error"));

        assertThrows(CsvReadException.class, () -> fileService.readMatrixAndInvert(file));
    }

}