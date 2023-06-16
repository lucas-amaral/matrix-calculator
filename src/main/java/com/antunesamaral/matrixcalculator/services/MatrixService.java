package com.antunesamaral.matrixcalculator.services;

import com.antunesamaral.matrixcalculator.exceptions.CalculationException;
import com.antunesamaral.matrixcalculator.entities.Delimiter;
import com.antunesamaral.matrixcalculator.entities.Matrix;
import com.antunesamaral.matrixcalculator.exceptions.MatrixException;
import com.antunesamaral.matrixcalculator.services.validations.MatrixValidator;
import com.antunesamaral.matrixcalculator.utils.ListUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class MatrixService {

    private final FileService fileService;

    public MatrixService(FileService fileService) {
        this.fileService = fileService;
    }

    public String echo(MultipartFile file) {
        final Matrix matrix = fileService.readMatrix(file);
        checkIfIsValid(matrix);
        return matrix.getRows().stream()
                .map(ListUtils.joiningElementsByComma())
                .collect(Collectors.joining(Delimiter.NEW_LINE.delimiter()));
    }

    public String flatten(MultipartFile file) {
        final Matrix matrix = fileService.readMatrix(file);
        checkIfIsValid(matrix);
        return matrix.getRows().stream()
                .map(ListUtils.joiningElementsByComma())
                .collect(Collectors.joining(Delimiter.COMMA.delimiter()));
    }

    public String invert(MultipartFile file) {
        List<List<String>> matrix = fileService.readMatrixAndInvert(file);
        checkIfIsValid(matrix);
        return matrix.stream()
                .map(row -> String.join(Delimiter.COMMA.delimiter(), row))
                .collect(Collectors.joining(Delimiter.NEW_LINE.delimiter()));
    }

    public BigInteger sum(MultipartFile file) {
        final Matrix matrix = fileService.readMatrix(file);
        checkIfIsValid(matrix);
        return matrix.getRows().parallelStream()
                .map(ListUtils.sumElements())
                .reduce(BigInteger::add)
                .orElseThrow(() -> new CalculationException("Error to sum matrix rows"));
    }

    public BigInteger multiply(MultipartFile file) {
        final Matrix matrix = fileService.readMatrix(file);
        checkIfIsValid(matrix);
        return matrix.getRows().parallelStream()
                .map(ListUtils.multiplyElements())
                .reduce(BigInteger::multiply)
                .orElseThrow(() -> new CalculationException("Error to multiply matrix rows"));
    }

    private void checkIfIsValid(Matrix matrix) {
        if (!MatrixValidator.isSquare(matrix)) {
            throw new MatrixException("Invalid Matrix. Please use a file that contains a square matrix");
        }
    }

    private void checkIfIsValid(List<List<String>> matrix) {
        if (!MatrixValidator.isSquare(matrix)) {
            throw new MatrixException("Invalid Matrix. Please use a file that contains a square matrix");
        }
        if (!MatrixValidator.hasOnlyIntegerNumbers(matrix)) {
            throw new MatrixException("Invalid Matrix. Please use a file that contains a matrix just with integer values");
        }
    }
}
