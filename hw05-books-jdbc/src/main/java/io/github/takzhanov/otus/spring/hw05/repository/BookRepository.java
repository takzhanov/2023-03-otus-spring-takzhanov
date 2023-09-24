package io.github.takzhanov.otus.spring.hw05.repository;

import io.github.takzhanov.otus.spring.hw05.domain.Book;
import java.util.List;

public interface BookRepository {
    List<Book> findAll();

    Book findById(Long id);

    Book create(Book entity);

    int update(Book entity);

    int delete(Long id);
}
