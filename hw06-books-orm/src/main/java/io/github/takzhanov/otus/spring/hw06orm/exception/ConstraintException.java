package io.github.takzhanov.otus.spring.hw06orm.exception;

public class ConstraintException extends RuntimeException {
    public ConstraintException() {
        super("Uniq constraint exception");
    }

    public ConstraintException(String msg) {
        super(msg);
    }
}
