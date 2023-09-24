package io.github.takzhanov.otus.spring.hw05.service;

import io.github.takzhanov.otus.spring.hw05.domain.Author;
import java.util.List;
import java.util.Set;

public interface AuthorService {
    List<Author> findAll();

    Author findOrCreateByName(String authorName);

    Set<Author> findOrCreateByName(String[] authorNames);

    Author create(Author newAuthor);

    Author update(Author updatedAuthor);

    void delete(Long id);

    void forceDelete(Long id);
}
