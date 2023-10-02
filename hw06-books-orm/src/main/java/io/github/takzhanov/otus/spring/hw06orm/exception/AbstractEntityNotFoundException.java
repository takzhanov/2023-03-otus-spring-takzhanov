package io.github.takzhanov.otus.spring.hw06orm.exception;

public abstract class AbstractEntityNotFoundException extends RuntimeException {

    public AbstractEntityNotFoundException(String message) {
        super(message);
    }
}
