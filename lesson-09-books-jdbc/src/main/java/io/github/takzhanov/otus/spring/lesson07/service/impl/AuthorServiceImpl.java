package io.github.takzhanov.otus.spring.lesson07.service.impl;

import io.github.takzhanov.otus.spring.lesson07.domain.Author;
import io.github.takzhanov.otus.spring.lesson07.exception.ConstraintException;
import io.github.takzhanov.otus.spring.lesson07.exception.EntityNotFoundException;
import io.github.takzhanov.otus.spring.lesson07.repository.AuthorRepository;
import io.github.takzhanov.otus.spring.lesson07.service.AuthorService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author findOrCreateByName(String authorName) {
        var author = authorRepository.findByName(authorName);
        return author != null ? author : authorRepository.create(new Author(null, authorName));
    }

    @Override
    public Set<Author> findOrCreateByName(String[] authorNames) {
        if (authorNames == null) return Collections.emptySet();
        return Arrays.stream(authorNames)
                .map(String::trim)
                .filter(Predicate.not(String::isEmpty))
                .distinct()
                .map(this::findOrCreateByName)
                .collect(Collectors.toSet());
    }

    @Override
    public Author create(Author newAuthor) {
        return authorRepository.create(newAuthor);
    }

    @Override
    public Author update(Author updatedAuthor) {
        int updatedRowCount = authorRepository.update(updatedAuthor);
        if (updatedRowCount == 0) {
            throw new EntityNotFoundException();
        }
        return updatedAuthor;
    }

    @Override
    public void delete(Long id) {
        try {
            authorRepository.delete(id);
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintException(e);
        }
    }

    @Override
    public void forceDelete(Long id) {
        authorRepository.forceDelete(id);
    }
}