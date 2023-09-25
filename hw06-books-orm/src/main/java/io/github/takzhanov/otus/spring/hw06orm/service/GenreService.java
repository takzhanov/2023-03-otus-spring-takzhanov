package io.github.takzhanov.otus.spring.hw06orm.service;

import io.github.takzhanov.otus.spring.hw06orm.domain.Genre;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GenreService {
    List<Genre> findAll();

    Optional<Genre> findById(Long id);

    Genre findOrCreateByName(String genreName);

    Set<Genre> findOrCreateByName(String[] genreNames);

    Genre create(Genre newGenre);

    Genre update(Genre updatedGenre);

    void delete(Long id);

    void forceDelete(Long id);
}
