package io.github.takzhanov.otus.spring.hw06orm.service.impl;

import io.github.takzhanov.otus.spring.hw06orm.domain.Genre;
import io.github.takzhanov.otus.spring.hw06orm.service.GenreFormatterService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class GenreFormatterServiceImpl implements GenreFormatterService {
    @Override
    public String formatGenre(Genre genre) {
        if (genre == null) {
            return "NULL";
        }
        return String.format("[ID: %d, Name: %s]", genre.getId(), genre.getName());
    }

    @Override
    public String formatGenres(List<Genre> genres) {
        return genres == null
                ? ""
                : genres.stream()
                .map(this::formatGenre)
                .collect(Collectors.joining("\n"));
    }
}
