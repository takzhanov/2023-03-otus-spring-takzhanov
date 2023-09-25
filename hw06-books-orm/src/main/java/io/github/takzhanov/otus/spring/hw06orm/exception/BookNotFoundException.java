package io.github.takzhanov.otus.spring.hw06orm.exception;

import io.github.takzhanov.otus.spring.hw06orm.domain.Book;

public class BookNotFoundException extends EntityNotFoundException {
    public BookNotFoundException(Book book) {
    }

    public BookNotFoundException(long id) {

    }
}
