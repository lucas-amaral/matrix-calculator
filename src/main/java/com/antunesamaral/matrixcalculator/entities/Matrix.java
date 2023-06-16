package com.antunesamaral.matrixcalculator.entities;

import java.math.BigInteger;
import java.util.List;

public class Matrix {
    
    private final List<List<BigInteger>> rows;

    public Matrix(List<List<BigInteger>> rows) {
        this.rows = rows;
    }

    public List<List<BigInteger>> getRows() {
        return rows;
    }
}
