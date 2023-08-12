package io.github.takzhanov.otus.spring.lesson07.repository;

import io.github.takzhanov.otus.spring.lesson07.domain.Author;

public interface AuthorRepository extends Repository<Long, Author> {
    Author findByName(String name);

    int forceDelete(Long id);
}
