package io.github.takzhanov.otus.spring.hw05.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class ConstraintException extends RuntimeException {
    public ConstraintException(DataIntegrityViolationException e) {
        super(e);
    }
}
