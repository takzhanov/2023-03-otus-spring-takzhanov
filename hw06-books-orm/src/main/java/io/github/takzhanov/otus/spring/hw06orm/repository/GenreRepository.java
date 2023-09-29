package io.github.takzhanov.otus.spring.hw06orm.repository;

import io.github.takzhanov.otus.spring.hw06orm.domain.Genre;
import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    List<Genre> findAll();

    Optional<Genre> findById(long id);

    Genre getById(long id);

    Optional<Genre> findByName(String name);

    Genre save(Genre entity);

    void delete(long id);

    void forceDelete(long id);
}
