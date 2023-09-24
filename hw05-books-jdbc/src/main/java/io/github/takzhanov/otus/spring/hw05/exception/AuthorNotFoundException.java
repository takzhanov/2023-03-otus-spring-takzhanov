package io.github.takzhanov.otus.spring.hw05.exception;

import io.github.takzhanov.otus.spring.hw05.domain.Author;

public class AuthorNotFoundException extends EntityNotFoundException {
    public AuthorNotFoundException(Author author) {
    }
}
