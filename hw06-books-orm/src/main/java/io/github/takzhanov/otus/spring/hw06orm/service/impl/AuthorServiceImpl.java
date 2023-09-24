package io.github.takzhanov.otus.spring.hw06orm.service.impl;

import io.github.takzhanov.otus.spring.hw06orm.domain.Author;
import io.github.takzhanov.otus.spring.hw06orm.exception.AuthorAlreadyExistException;
import io.github.takzhanov.otus.spring.hw06orm.exception.AuthorNotFoundException;
import io.github.takzhanov.otus.spring.hw06orm.exception.ConstraintException;
import io.github.takzhanov.otus.spring.hw06orm.repository.AuthorRepository;
import io.github.takzhanov.otus.spring.hw06orm.service.AuthorService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    @Transactional
    public Author findOrCreateByName(String authorName) {
        var author = authorRepository.findByName(authorName);
        return author != null ? author : authorRepository.create(new Author(null, authorName));
    }

    @Override
    @Transactional
    public Set<Author> findOrCreateByName(String[] authorNames) {
        if (authorNames == null) {
            return Collections.emptySet();
        }
        return Arrays.stream(authorNames)
                .map(String::trim)
                .filter(Predicate.not(String::isEmpty))
                .distinct()
                .map(this::findOrCreateByName)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public Author create(Author newAuthor) {
        return authorRepository.create(newAuthor);
    }

    @Override
    @Transactional
    public Author update(Author updatedAuthor) {
        try {
            int updatedRowCount = authorRepository.update(updatedAuthor);
            if (updatedRowCount == 0) {
                throw new AuthorNotFoundException(updatedAuthor);
            }
            return updatedAuthor;
        } catch (DuplicateKeyException e) {
            throw new AuthorAlreadyExistException(updatedAuthor);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            authorRepository.delete(id);
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintException(e);
        }
    }

    @Override
    @Transactional
    public void forceDelete(Long id) {
        authorRepository.forceDelete(id);
    }
}
