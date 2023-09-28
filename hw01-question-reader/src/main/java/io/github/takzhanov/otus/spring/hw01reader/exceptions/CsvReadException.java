package io.github.takzhanov.otus.spring.hw01reader.exceptions;

public class CsvReadException extends RuntimeException {
    public CsvReadException(String message, Throwable ex) {
        super(message, ex);
    }
}
