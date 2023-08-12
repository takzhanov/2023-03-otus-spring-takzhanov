package io.github.takzhanov.otus.spring.lesson07.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class ConstraintException extends RuntimeException {
    public ConstraintException(DataIntegrityViolationException e) {
        super(e);
    }
}
