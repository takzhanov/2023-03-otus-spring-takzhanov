package io.github.takzhanov.otus.spring.hw06orm.service.formatter.converter;

import io.github.takzhanov.otus.spring.hw06orm.domain.Author;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AuthorConverter implements Converter<Author, String> {
    @Override
    public String convert(Author author) {
        if (author == null) {
            return "NULL";
        }
        return String.format("[ID: %d, Name: %s]", author.getId(), author.getName());
    }
}
