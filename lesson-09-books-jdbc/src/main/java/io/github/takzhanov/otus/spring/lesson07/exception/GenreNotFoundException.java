package io.github.takzhanov.otus.spring.lesson07.exception;

import io.github.takzhanov.otus.spring.lesson07.domain.Genre;

public class GenreNotFoundException extends EntityNotFoundException {
    public GenreNotFoundException(Genre genre) {
    }
}
