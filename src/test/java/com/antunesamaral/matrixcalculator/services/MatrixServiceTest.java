package com.antunesamaral.matrixcalculator.services;

import com.antunesamaral.matrixcalculator.entities.Matrix;
import com.antunesamaral.matrixcalculator.exceptions.MatrixException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class MatrixServiceTest {

    @MockBean
    private FileService fileService;

    private MatrixService matrixService;

    @BeforeEach
    void setUp() {
        matrixService = new MatrixService(fileService);
    }

    @Test
    void echo() {
        final MultipartFile file = mock(MultipartFile.class);

        final Matrix matrix = generateMatrix();

        when(fileService.readMatrix(file)).thenReturn(matrix);

        final String expectedResponse = "1,2,3\n" +
                "4,5,6\n" +
                "7,8,9";

        assertThat(matrixService.echo(file))
                .isNotNull()
                .isEqualTo(expectedResponse);
    }

    @Test
    void echo_shouldThrowExceptionWhenMatrixIsNotSquare() {
        final MultipartFile file = mock(MultipartFile.class);

        final Matrix matrix = generateNotSquareMatrix();

        when(fileService.readMatrix(file)).thenReturn(matrix);

        assertThrows(MatrixException.class, () -> matrixService.echo(file));
    }

    @Test
    void flatten() {
        final MultipartFile file = mock(MultipartFile.class);

        final Matrix matrix = generateMatrix();

        when(fileService.readMatrix(file)).thenReturn(matrix);

        final String expectedResponse = "1,2,3,4,5,6,7,8,9";

        assertThat(matrixService.flatten(file))
                .isNotNull()
                .isEqualTo(expectedResponse);
    }

    @Test
    void flatten_shouldThrowExceptionWhenMatrixIsNotSquare() {
        final MultipartFile file = mock(MultipartFile.class);

        final Matrix matrix = generateNotSquareMatrix();

        when(fileService.readMatrix(file)).thenReturn(matrix);

        assertThrows(MatrixException.class, () -> matrixService.flatten(file));
    }

    @Test
    void invert() {
        final MultipartFile file = mock(MultipartFile.class);

        final List<List<String>> matrix = Arrays.asList(
            Arrays.asList("1","4","7"),
            Arrays.asList("2","5","8"),
            Arrays.asList("3","6","9")
        );

        when(fileService.readMatrixAndInvert(file)).thenReturn(matrix);

        final String expectedResponse = "1,4,7\n" +
                "2,5,8\n" +
                "3,6,9";

        assertThat(matrixService.invert(file))
                .isNotNull()
                .isEqualTo(expectedResponse);
    }

    @Test
    void invert_shouldThrowExceptionWhenMatrixIsNotSquare() {
        final MultipartFile file = mock(MultipartFile.class);

        final List<List<String>> matrix = Arrays.asList(
                Arrays.asList("1","4","7"),
                Arrays.asList("2","5","8", "10"),
                Arrays.asList("3","6","9")
        );

        when(fileService.readMatrixAndInvert(file)).thenReturn(matrix);

        assertThrows(MatrixException.class, () -> matrixService.invert(file));
    }

    @Test
    void invert_shouldThrowExceptionWhenMatrixHasNonIntegerValue() {
        final MultipartFile file = mock(MultipartFile.class);

        final List<List<String>> matrix = Arrays.asList(
                Arrays.asList("1","4","7"),
                Arrays.asList("2","a","8"),
                Arrays.asList("3","6","9")
        );

        when(fileService.readMatrixAndInvert(file)).thenReturn(matrix);

        assertThrows(MatrixException.class, () -> matrixService.invert(file));
    }

    @Test
    void sum() {
        final MultipartFile file = mock(MultipartFile.class);

        final Matrix matrix = generateMatrix();

        when(fileService.readMatrix(file)).thenReturn(matrix);

        final String expectedResponse = "1,4,7\n" +
                "2,5,8\n" +
                "3,6,9";

        assertThat(matrixService.sum(file))
                .isNotNull()
                .isEqualTo(45);
    }



    @Test
    void sum_shouldThrowExceptionWhenMatrixIsNotSquare() {
        final MultipartFile file = mock(MultipartFile.class);

        final Matrix matrix = generateNotSquareMatrix();

        when(fileService.readMatrix(file)).thenReturn(matrix);

        assertThrows(MatrixException.class, () -> matrixService.sum(file));
    }

    @Test
    void multiply() {
        final MultipartFile file = mock(MultipartFile.class);

        final Matrix matrix = generateMatrix();

        when(fileService.readMatrix(file)).thenReturn(matrix);

        final String expectedResponse = "1,4,7\n" +
                "2,5,8\n" +
                "3,6,9";

        assertThat(matrixService.multiply(file))
                .isNotNull()
                .isEqualTo(362880);
    }

    @Test
    void multiply_shouldThrowExceptionWhenMatrixIsNotSquare() {
        final MultipartFile file = mock(MultipartFile.class);

        final Matrix matrix = generateNotSquareMatrix();

        when(fileService.readMatrix(file)).thenReturn(matrix);

        assertThrows(MatrixException.class, () -> matrixService.multiply(file));
    }

    private static Matrix generateMatrix() {
        List<BigInteger> row1 = Arrays.asList(new BigInteger("1"), new BigInteger("2"), new BigInteger("3"));
        List<BigInteger> row2 = Arrays.asList(new BigInteger("4"), new BigInteger("5"), new BigInteger("6"));
        List<BigInteger> row3 = Arrays.asList(new BigInteger("7"), new BigInteger("8"), new BigInteger("9"));
        return new Matrix(Arrays.asList(row1, row2, row3));
    }

    private static Matrix generateNotSquareMatrix() {
        List<BigInteger> row1 = Arrays.asList(new BigInteger("1"), new BigInteger("2"));
        List<BigInteger> row2 = Arrays.asList(new BigInteger("4"), new BigInteger("5"), new BigInteger("6"));
        return new Matrix(Arrays.asList(row1, row2));
    }
}