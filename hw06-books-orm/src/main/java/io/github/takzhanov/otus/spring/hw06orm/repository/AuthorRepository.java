package io.github.takzhanov.otus.spring.hw06orm.repository;

import io.github.takzhanov.otus.spring.hw06orm.domain.Author;
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
