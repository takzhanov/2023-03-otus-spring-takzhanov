package io.github.takzhanov.otus.spring.lesson07.repository;

import io.github.takzhanov.otus.spring.lesson07.domain.Genre;

public interface GenreRepository extends Repository<Long, Genre> {
    Genre findByName(String name);

    int forceDelete(Long id);
}
