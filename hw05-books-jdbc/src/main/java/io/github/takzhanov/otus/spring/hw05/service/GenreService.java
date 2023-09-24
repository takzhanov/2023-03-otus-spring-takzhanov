package io.github.takzhanov.otus.spring.hw05.service;

import io.github.takzhanov.otus.spring.hw05.domain.Genre;
import java.util.List;
import java.util.Set;

public interface GenreService {
    List<Genre> findAll();

    Genre findById(Long id);

    Genre findOrCreateByName(String genreName);

    Set<Genre> findOrCreateByName(String[] genreNames);

    Genre create(Genre newGenre);

    Genre update(Genre updatedGenre);

    void delete(Long id);

    void forceDelete(Long id);
}
