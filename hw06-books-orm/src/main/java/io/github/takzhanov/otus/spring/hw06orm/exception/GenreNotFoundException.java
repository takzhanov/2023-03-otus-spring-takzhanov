package io.github.takzhanov.otus.spring.hw06orm.exception;

public class GenreNotFoundException extends AbstractEntityNotFoundException {

    public GenreNotFoundException(long id) {
        super("Genre with id %d is not found".formatted(id));
    }
}
