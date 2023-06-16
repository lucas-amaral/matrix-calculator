package com.antunesamaral.matrixcalculator.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class MatrixException extends RuntimeException {
    private static final Logger LOGGER = LoggerFactory.getLogger(MatrixException.class);

    public MatrixException(String message) {
        super(message);
        LOGGER.error(message);
    }
}
