package io.github.takzhanov.otus.spring.hw05.repository;

import io.github.takzhanov.otus.spring.hw05.domain.Author;
import java.util.List;

public interface AuthorRepository {
    Author findByName(String name);

    int forceDelete(Long id);

    List<Author> findAll();

    Author findById(Long id);

    Author create(Author entity);

    int update(Author entity);

    int delete(Long id);
}
