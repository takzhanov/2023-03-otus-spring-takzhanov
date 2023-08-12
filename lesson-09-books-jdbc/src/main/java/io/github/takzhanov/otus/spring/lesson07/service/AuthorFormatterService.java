package io.github.takzhanov.otus.spring.lesson07.service;

import io.github.takzhanov.otus.spring.lesson07.domain.Author;
import java.util.List;

public interface AuthorFormatterService {
    String formatAuthor(Author author);

    String formatAuthors(List<Author> authors);
}
