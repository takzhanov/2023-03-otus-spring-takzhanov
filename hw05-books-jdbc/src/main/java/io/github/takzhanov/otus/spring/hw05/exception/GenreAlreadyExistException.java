package io.github.takzhanov.otus.spring.hw05.exception;

import io.github.takzhanov.otus.spring.hw05.domain.Genre;

public class GenreAlreadyExistException extends EntityAlreadyExistsException {
    public GenreAlreadyExistException(Genre genre) {
    }
}
