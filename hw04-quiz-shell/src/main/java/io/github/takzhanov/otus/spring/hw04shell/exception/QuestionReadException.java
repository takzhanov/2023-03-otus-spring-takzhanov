package io.github.takzhanov.otus.spring.hw04shell.exception;

public class QuestionReadException extends RuntimeException {
    public QuestionReadException(String message, Throwable ex) {
        super(message, ex);
    }
}
