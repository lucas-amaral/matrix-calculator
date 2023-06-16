package com.antunesamaral.matrixcalculator.services;

import com.antunesamaral.matrixcalculator.entities.Delimiter;
import com.antunesamaral.matrixcalculator.entities.Matrix;
import com.antunesamaral.matrixcalculator.exceptions.CsvReadException;
import com.antunesamaral.matrixcalculator.exceptions.MatrixException;
import com.antunesamaral.matrixcalculator.utils.ListUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

@Service
public class FileService {

    private static final String EXTENSION_CSV = ".csv";

    /**
     * Creates a {@link Matrix} with the rows and values read on csv file
     *
     * @param file the multipartFile received by API
     * @return {@code Matrix}
     */
    public Matrix readMatrix(MultipartFile file) {
        checkIfIsCsv(file);
        final List<List<BigInteger>> matrix = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String row;
            while ((row = br.readLine()) != null) {
                matrix.add(createRow(row));
            }
            return new Matrix(matrix);
        } catch (IOException e) {
            throw new CsvReadException("Error to read file: " + file.getName());
        }
    }

    /**
     * Creates a {@link List<BigInteger>} with the integer values found on current row
     *
     * @param row the current row of the csv file
     * @return {@code List<BigInteger>}
     */
    private static List<BigInteger> createRow(String row) {
        String[] values = row.split(rowDelimiter(row));
        try {
            return ListUtils.convertArrayToBigIntegerList(values);
        } catch (NumberFormatException e) {
            throw new MatrixException("Invalid Matrix. Please use a file that contains a matrix just " +
                    "with integer values and without header row");
        }
    }

    /**
     * Creates a {@link List<List<String>>} that correspond a inverted matrix read on csv file
     *
     * @param file the multipartFile received by API
     * @return {@code List<List<String>>}
     */
    public List<List<String>> readMatrixAndInvert(MultipartFile file) {
        checkIfIsCsv(file);
        Map<Integer, List<String>> matrix = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String row;
            while ((row = br.readLine()) != null) {
                addInvertedValues(matrix, row);
            }
            return new ArrayList<>(matrix.values());
        } catch (IOException e) {
            throw new CsvReadException("Error to read file: " + file.getName());
        }
    }

    /**
     * Add the value on the inverted way.
     * E.g. If the value is on the second position of the row,
     * add it on the next position on the second row of the matrix
     */
    private void addInvertedValues(Map<Integer, List<String>> matrix, String row) {
        String[] values = row.split(rowDelimiter(row));
        int index = 0;
        for (String value : values) {
            matrix.putIfAbsent(index, new ArrayList<>());
            matrix.computeIfPresent(index, addValueRow(value));
            index++;
        }
    }

    /**
     * Add the value on the next position of list and return the list
     */
    private BiFunction<Integer, List<String>, List<String>> addValueRow(String value) {
        return (key, val) -> {
            val.add(value);
            return val;
        };
    }

    /**
     * Find the delimiter for the current row
     */
    private static String rowDelimiter(String row) {
        return Delimiter.of(row).delimiter();
    }

    private static void checkIfIsCsv(MultipartFile file) {
        if (file.getOriginalFilename() == null || !file.getOriginalFilename().endsWith(EXTENSION_CSV)) {
            throw new CsvReadException("File extension not supported. Please use csv file");
        }
    }
}
