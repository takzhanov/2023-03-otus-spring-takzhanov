package io.github.takzhanov.otus.spring.hw02quiz.exceptions;

public class QuestionReadException extends RuntimeException {
    public QuestionReadException(String message, Throwable ex) {
        super(message, ex);
    }
}
