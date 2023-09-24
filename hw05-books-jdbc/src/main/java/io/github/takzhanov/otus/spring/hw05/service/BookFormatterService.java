package io.github.takzhanov.otus.spring.hw05.service;

import io.github.takzhanov.otus.spring.hw05.domain.Book;
import java.util.List;

public interface BookFormatterService {
    String formatBook(Book book);

    String formatBooks(List<Book> books);
}
