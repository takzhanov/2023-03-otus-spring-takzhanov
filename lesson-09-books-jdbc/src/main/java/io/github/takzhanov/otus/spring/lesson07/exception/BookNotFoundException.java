package io.github.takzhanov.otus.spring.lesson07.exception;

import io.github.takzhanov.otus.spring.lesson07.domain.Book;

public class BookNotFoundException extends EntityNotFoundException {
    public BookNotFoundException(Book book) {
    }
}
