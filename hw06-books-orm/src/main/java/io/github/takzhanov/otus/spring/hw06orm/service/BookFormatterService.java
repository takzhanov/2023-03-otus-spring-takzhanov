package io.github.takzhanov.otus.spring.hw06orm.service;

import io.github.takzhanov.otus.spring.hw06orm.domain.Book;
import java.util.List;

public interface BookFormatterService {
    String formatBook(Book book);

    String formatBooks(List<Book> books);
}
