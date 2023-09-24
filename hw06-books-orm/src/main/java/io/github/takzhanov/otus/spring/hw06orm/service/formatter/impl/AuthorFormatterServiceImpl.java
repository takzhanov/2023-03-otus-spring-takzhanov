package io.github.takzhanov.otus.spring.hw06orm.service.formatter.impl;

import io.github.takzhanov.otus.spring.hw06orm.domain.Author;
import io.github.takzhanov.otus.spring.hw06orm.service.formatter.AuthorFormatterService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class AuthorFormatterServiceImpl implements AuthorFormatterService {
    @Override
    public String formatAuthor(Author author) {
        if (author == null) {
            return "NULL";
        }
        return String.format("[ID: %d, Name: %s]", author.getId(), author.getName());
    }

    @Override
    public String formatAuthors(List<Author> authors) {
        return authors == null
                ? ""
                : authors.stream()
                .map(this::formatAuthor)
                .collect(Collectors.joining("\n"));
    }
}