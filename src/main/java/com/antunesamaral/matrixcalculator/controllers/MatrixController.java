package com.antunesamaral.matrixcalculator.controllers;

import com.antunesamaral.matrixcalculator.exceptions.CalculationException;
import com.antunesamaral.matrixcalculator.exceptions.CsvReadException;
import com.antunesamaral.matrixcalculator.exceptions.MatrixException;
import com.antunesamaral.matrixcalculator.services.MatrixService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping
public class MatrixController {

    private final MatrixService matrixService;

    public MatrixController(MatrixService matrixService) {
        this.matrixService = matrixService;
    }

    @PostMapping(path = "/echo", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> echo(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(matrixService.echo(file));
    }

    @PostMapping(path = "/flatten", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @ExceptionHandler(value = { CsvReadException.class, MatrixException.class, CalculationException.class })
    public ResponseEntity<?> flatten(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(matrixService.flatten(file));
    }

    @PostMapping(path = "/invert", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> invert(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(matrixService.invert(file));
    }

    @PostMapping(path = "/sum", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> sum(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(matrixService.sum(file));
    }

    @PostMapping(path = "/multiply", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> multiply(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(matrixService.multiply(file));
    }
}
