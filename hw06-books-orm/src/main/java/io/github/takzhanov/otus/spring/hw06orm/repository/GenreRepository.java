package io.github.takzhanov.otus.spring.hw06orm.repository;

import io.github.takzhanov.otus.spring.hw06orm.domain.Genre;
import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    Optional<Genre> findByName(String name);

    int forceDelete(Long id);

    List<Genre> findAll();

    Optional<Genre> findById(Long id);

    Genre save(Genre entity);

    int delete(Long id);
}
