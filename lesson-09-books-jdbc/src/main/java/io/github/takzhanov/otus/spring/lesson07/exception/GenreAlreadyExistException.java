package io.github.takzhanov.otus.spring.lesson07.exception;

import io.github.takzhanov.otus.spring.lesson07.domain.Genre;

public class GenreAlreadyExistException extends EntityAlreadyExistsException {
    public GenreAlreadyExistException(Genre genre) {
    }
}