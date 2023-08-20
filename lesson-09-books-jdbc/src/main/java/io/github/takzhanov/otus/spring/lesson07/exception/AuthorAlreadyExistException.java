package io.github.takzhanov.otus.spring.lesson07.exception;

import io.github.takzhanov.otus.spring.lesson07.domain.Author;

public class AuthorAlreadyExistException extends EntityAlreadyExistsException {
    public AuthorAlreadyExistException(Author author) {
    }
}
