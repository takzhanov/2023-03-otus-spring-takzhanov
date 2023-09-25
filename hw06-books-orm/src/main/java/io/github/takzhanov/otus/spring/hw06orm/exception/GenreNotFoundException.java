package io.github.takzhanov.otus.spring.hw06orm.exception;

import io.github.takzhanov.otus.spring.hw06orm.domain.Genre;

public class GenreNotFoundException extends EntityNotFoundException {
    public GenreNotFoundException(Genre genre) {
    }

    public GenreNotFoundException(long genreId) {
    }
}
