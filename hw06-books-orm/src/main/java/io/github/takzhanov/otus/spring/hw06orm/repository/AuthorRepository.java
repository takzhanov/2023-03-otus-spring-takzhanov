package io.github.takzhanov.otus.spring.hw06orm.repository;

import io.github.takzhanov.otus.spring.hw06orm.domain.Author;
import java.util.List;
import java.util.Optional;

public interface AuthorRepository {
    Optional<Author> findByName(String name);

    void forceDelete(Long id);

    List<Author> findAll();

    Optional<Author> findById(Long id);

    Author create(Author entity);

    Author update(Author entity);

    void delete(Long id);
}
