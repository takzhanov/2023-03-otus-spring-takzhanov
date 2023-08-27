package io.github.takzhanov.otus.spring.lesson07.service;

import io.github.takzhanov.otus.spring.lesson07.domain.Book;
import java.util.List;

public interface BookFormatterService {
    String formatBook(Book book);

    String formatBooks(List<Book> books);
}
