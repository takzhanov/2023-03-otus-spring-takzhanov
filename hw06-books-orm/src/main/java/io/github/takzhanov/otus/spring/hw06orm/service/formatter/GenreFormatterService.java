package io.github.takzhanov.otus.spring.hw06orm.service.formatter;

import io.github.takzhanov.otus.spring.hw06orm.domain.Genre;
import java.util.List;

public interface GenreFormatterService {
    String formatGenre(Genre genre);

    String formatGenres(List<Genre> genres);
}
