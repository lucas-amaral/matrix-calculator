package com.antunesamaral.matrixcalculator.controllers;

import com.antunesamaral.matrixcalculator.exceptions.MatrixException;
import com.antunesamaral.matrixcalculator.services.MatrixService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigInteger;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = MatrixController.class)
class MatrixControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MatrixService matrixService;

    @Test
    void echo() throws Exception {
        final String responseBody = "1,4,7\n" +
                "2,5,8\n" +
                "3,6,9";
        final MockMultipartFile file = generateFile(responseBody);

        when(matrixService.echo(file)).thenReturn(responseBody);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/echo").file(file))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));

        verify(matrixService).echo(file);
    }

    @Test
    void echo_shouldReturnsUnprocessableEntityHttpStatusWhenMatrixIsNotSquare() throws Exception {
        final String responseBody = "1,4,7\n" +
                "2,5,8,10\n" +
                "3,6,9";

        final MockMultipartFile file = generateFile(responseBody);

        when(matrixService.echo(file))
                .thenThrow(new MatrixException("Invalid Matrix. Please use a file that contains a square matrix"));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/echo").file(file))
                .andExpect(status().isUnprocessableEntity());

        verify(matrixService).echo(file);
    }

    @Test
    void flatten() throws Exception {
        final String responseBody = "1,4,7,2,5,8,3,6,9";
        final MockMultipartFile file = generateFile(responseBody);

        when(matrixService.flatten(file)).thenReturn(responseBody);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/flatten").file(file))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));

        verify(matrixService).flatten(file);
    }

    @Test
    void flatten_shouldReturnsUnprocessableEntityHttpStatusWhenMatrixIsNotSquare() throws Exception {
        final String responseBody = "1,4,7,2,5,8,3,6,9";
        final MockMultipartFile file = generateFile(responseBody);

        when(matrixService.flatten(file))
                .thenThrow(new MatrixException("Invalid Matrix. Please use a file that contains a square matrix"));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/flatten").file(file))
                .andExpect(status().isUnprocessableEntity());

        verify(matrixService).flatten(file);
    }

    @Test
    void invert() throws Exception {
        final String responseBody = "1,4,7\n" +
                "2,5,8\n" +
                "3,6,9";
        final MockMultipartFile file = generateFile(responseBody);

        when(matrixService.invert(file)).thenReturn(responseBody);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/invert").file(file))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody));

        verify(matrixService).invert(file);
    }

    @Test
    void invert_shouldReturnsUnprocessableEntityHttpStatusWhenMatrixIsNotSquare() throws Exception {
        final String responseBody = "1,4,7\n" +
                "2,5,8\n" +
                "3,6,9";
        final MockMultipartFile file = generateFile(responseBody);

        when(matrixService.invert(file))
                .thenThrow(new MatrixException("Invalid Matrix. Please use a file that contains a square matrix"));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/invert").file(file))
                .andExpect(status().isUnprocessableEntity());

        verify(matrixService).invert(file);
    }

    @Test
    void sum() throws Exception {
        final BigInteger responseBody = BigInteger.valueOf(234);
        final MockMultipartFile file = generateFile("");

        when(matrixService.sum(file)).thenReturn(responseBody);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/sum").file(file))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody.toString()));

        verify(matrixService).sum(file);
    }

    @Test
    void sum_shouldReturnsUnprocessableEntityHttpStatusWhenMatrixIsNotSquare() throws Exception {
        final MockMultipartFile file = generateFile("");

        when(matrixService.sum(file))
                .thenThrow(new MatrixException("Invalid Matrix. Please use a file that contains a square matrix"));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/sum").file(file))
                .andExpect(status().isUnprocessableEntity());

        verify(matrixService).sum(file);
    }

    @Test
    void multiply() throws Exception {
        final BigInteger responseBody = BigInteger.valueOf(234);
        final MockMultipartFile file = generateFile("");

        when(matrixService.multiply(file)).thenReturn(responseBody);

        mockMvc.perform(MockMvcRequestBuilders.multipart("/multiply").file(file))
                .andExpect(status().isOk())
                .andExpect(content().string(responseBody.toString()));

        verify(matrixService).multiply(file);
    }

    @Test
    void multiply_shouldReturnsUnprocessableEntityHttpStatusWhenMatrixIsNotSquare() throws Exception {
        final MockMultipartFile file = generateFile("");

        when(matrixService.multiply(file))
                .thenThrow(new MatrixException("Invalid Matrix. Please use a file that contains a square matrix"));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/multiply").file(file))
                .andExpect(status().isUnprocessableEntity());

        verify(matrixService).multiply(file);
    }

    private static MockMultipartFile generateFile(String responseBody) {
        return new MockMultipartFile(
                "file",
                "matrix.csv",
                MediaType.MULTIPART_FORM_DATA_VALUE,
                responseBody.getBytes()
        );
    }
}