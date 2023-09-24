package io.github.takzhanov.otus.spring.hw05.exception;

import io.github.takzhanov.otus.spring.hw05.domain.Genre;

public class GenreNotFoundException extends EntityNotFoundException {
    public GenreNotFoundException(Genre genre) {
    }
}
