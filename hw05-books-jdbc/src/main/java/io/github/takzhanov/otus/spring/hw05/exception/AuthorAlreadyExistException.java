package io.github.takzhanov.otus.spring.hw05.exception;

import io.github.takzhanov.otus.spring.hw05.domain.Author;

public class AuthorAlreadyExistException extends EntityAlreadyExistsException {
    public AuthorAlreadyExistException(Author author) {
    }
}
