package io.github.takzhanov.otus.spring.hw06orm.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class ConstraintException extends RuntimeException {
    public ConstraintException(DataIntegrityViolationException e) {
        super(e);
    }
}
