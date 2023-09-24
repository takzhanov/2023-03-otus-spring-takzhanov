package io.github.takzhanov.otus.spring.hw06orm.exception;

import io.github.takzhanov.otus.spring.hw06orm.domain.Genre;

public class GenreAlreadyExistException extends EntityAlreadyExistsException {
    public GenreAlreadyExistException(Genre genre) {
    }
}
