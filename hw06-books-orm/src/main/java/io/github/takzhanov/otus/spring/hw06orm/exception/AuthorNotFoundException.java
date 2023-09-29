package io.github.takzhanov.otus.spring.hw06orm.exception;

public class AuthorNotFoundException extends AbstractEntityNotFoundException {

    public AuthorNotFoundException(long id) {
        super("Author with id %d is not found".formatted(id));
    }
}
