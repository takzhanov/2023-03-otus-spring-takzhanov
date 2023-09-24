package io.github.takzhanov.otus.spring.hw06orm.repository;

import io.github.takzhanov.otus.spring.hw06orm.domain.Book;
import java.util.List;

public interface BookRepository {
    List<Book> findAll();

    Book findById(Long id);

    Book create(Book entity);

    int update(Book entity);

    int delete(Long id);
}
