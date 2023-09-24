package io.github.takzhanov.otus.spring.hw06orm.exception;

import io.github.takzhanov.otus.spring.hw06orm.domain.Author;

public class AuthorAlreadyExistException extends EntityAlreadyExistsException {
    public AuthorAlreadyExistException(Author author) {
    }
}
