package io.github.takzhanov.otus.spring.lesson07.service;

import io.github.takzhanov.otus.spring.lesson07.domain.Genre;
import java.util.List;

public interface GenreFormatterService {
    String formatGenre(Genre genre);

    String formatGenres(List<Genre> genres);
}
