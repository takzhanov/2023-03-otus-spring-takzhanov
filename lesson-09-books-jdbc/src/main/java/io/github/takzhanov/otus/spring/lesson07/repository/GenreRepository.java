package io.github.takzhanov.otus.spring.lesson07.repository;

import io.github.takzhanov.otus.spring.lesson07.domain.Genre;
import java.util.List;

public interface GenreRepository {
    Genre findByName(String name);

    int forceDelete(Long id);

    List<Genre> findAll();

    Genre findById(Long id);

    Genre create(Genre entity);

    int update(Genre entity);

    int delete(Long id);
}
