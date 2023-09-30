package io.github.takzhanov.otus.spring.hw06orm.service;

import io.github.takzhanov.otus.spring.hw06orm.domain.Author;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AuthorService {
    List<Author> findAll();

    Author findOrCreateByName(String authorName);

    Optional<Author> findById(long authorId);

    Author getById(long authorId);

    Set<Author> findOrCreateByName(String[] authorNames);

    Author create(Author newAuthor);

    Author update(Author updatedAuthor);

    void delete(long id);

    void forceDelete(long id);
}
