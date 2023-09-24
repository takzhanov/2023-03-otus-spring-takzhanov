package io.github.takzhanov.otus.spring.hw05.exception;

import io.github.takzhanov.otus.spring.hw05.domain.Book;

public class BookNotFoundException extends EntityNotFoundException {
    public BookNotFoundException(Book book) {
    }
}
