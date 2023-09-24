package io.github.takzhanov.otus.spring.hw05.service;

import io.github.takzhanov.otus.spring.hw05.domain.Author;
import java.util.List;

public interface AuthorFormatterService {
    String formatAuthor(Author author);

    String formatAuthors(List<Author> authors);
}
