package io.github.takzhanov.otus.spring.lesson07.repository;

import io.github.takzhanov.otus.spring.lesson07.domain.Book;
import java.util.List;

public interface BookRepository {
    List<Book> findAll();

    Book findById(Long id);

    Book create(Book entity);

    int update(Book entity);

    int delete(Long id);
}
