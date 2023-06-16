package com.antunesamaral.matrixcalculator.entities;

import com.antunesamaral.matrixcalculator.exceptions.CsvReadException;

import java.util.Arrays;

public enum Delimiter {
    COMMA {
        public String delimiter() {
            return ",";
        }
    },
    SEMICOLON {
        public String delimiter() {
            return ";";
        }
    },
    TAB {
        public String delimiter() {
            return "\t";
        }
    },
    SPACE {
        public String delimiter() {
            return " ";
        }
    },
    NEW_LINE {
        public String delimiter() {
            return "\n";
        }
    };

    public abstract String delimiter();

    public static Delimiter of(String row) {
        return Arrays.stream(Delimiter.values())
                .filter(delimiter -> row.contains(delimiter.delimiter()))
                .findAny()
                .orElseThrow(() -> new CsvReadException("Csv file delimiter not accepted. Valid formats: (',', ';', '\\t', ' ')"));
    }
}
