package io.github.takzhanov.otus.spring.hw06orm.service;

import io.github.takzhanov.otus.spring.hw06orm.domain.Author;
import java.util.List;

public interface AuthorFormatterService {
    String formatAuthor(Author author);

    String formatAuthors(List<Author> authors);
}
