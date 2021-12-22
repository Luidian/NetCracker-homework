package com.example.parser;

public class CsvParseException extends Exception {

    public CsvParseException(String message) {
        super(message);
    }

    public CsvParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public CsvParseException(Throwable cause) {
        super(cause);
    }

}
