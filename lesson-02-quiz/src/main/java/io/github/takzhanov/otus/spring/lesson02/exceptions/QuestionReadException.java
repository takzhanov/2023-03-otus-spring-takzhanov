package io.github.takzhanov.otus.spring.lesson02.exceptions;

public class QuestionReadException extends RuntimeException {
    public QuestionReadException(String message, Throwable ex) {
        super(message, ex);
    }
}
