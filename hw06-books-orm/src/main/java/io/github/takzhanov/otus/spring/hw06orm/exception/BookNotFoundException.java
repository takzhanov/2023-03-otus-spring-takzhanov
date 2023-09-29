package io.github.takzhanov.otus.spring.hw06orm.exception;

public class BookNotFoundException extends AbstractEntityNotFoundException {

    public BookNotFoundException(long id) {
        super("Book with id %d is not found".formatted(id));
    }
}
