package io.github.takzhanov.otus.spring.hw06orm.service.formatter.converter;

import io.github.takzhanov.otus.spring.hw06orm.domain.Genre;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GenreConverter implements Converter<Genre, String> {
    @Override
    public String convert(Genre genre) {
        if (genre == null) {
            return "NULL";
        }
        return String.format("[ID: %d, Name: %s]", genre.getId(), genre.getName());
    }
}
