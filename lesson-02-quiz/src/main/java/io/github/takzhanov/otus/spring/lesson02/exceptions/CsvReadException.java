package io.github.takzhanov.otus.spring.lesson02.exceptions;

public class CsvReadException extends RuntimeException {
    public CsvReadException(String message, Throwable ex) {
        super(message, ex);
    }
}
