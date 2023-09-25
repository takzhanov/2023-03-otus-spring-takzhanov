package io.github.takzhanov.otus.spring.hw06orm.exception;

import io.github.takzhanov.otus.spring.hw06orm.domain.Author;

public class AuthorNotFoundException extends EntityNotFoundException {
    public AuthorNotFoundException(Author author) {
    }

    public AuthorNotFoundException(long authorId) {
    }
}
