package io.github.takzhanov.otus.spring.hw06orm.service.impl;

import io.github.takzhanov.otus.spring.hw06orm.domain.Author;
import io.github.takzhanov.otus.spring.hw06orm.exception.AuthorNotFoundException;
import io.github.takzhanov.otus.spring.hw06orm.exception.ConstraintException;
import io.github.takzhanov.otus.spring.hw06orm.repository.AuthorRepository;
import io.github.takzhanov.otus.spring.hw06orm.service.AuthorService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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
    @Transactional(readOnly = true)
    public Optional<Author> findById(long authorId) {
        return authorRepository.findById(authorId);
    }

    @Override
    @Transactional(readOnly = true)
    public Author getById(long authorId) {
        return findById(authorId).orElseThrow(() -> new AuthorNotFoundException(authorId));
    }

    @Override
    @Transactional
    public Author findOrCreateByName(String authorName) {
        return authorRepository.findByName(authorName)
                .orElseGet(() -> authorRepository.save(new Author(authorName)));
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
        try {
            return authorRepository.save(newAuthor);
        } catch (DataIntegrityViolationException ex) {
            throw new ConstraintException("Author with name '%s' already exists".formatted(newAuthor.getName()));
        }
    }

    @Override
    @Transactional
    public Author update(Author updatedAuthor) {
        var author = getById(updatedAuthor.getId());
        author.setName(updatedAuthor.getName());
        return authorRepository.save(author);
    }

    @Override
    @Transactional
    public void delete(long id) {
        authorRepository.deleteById(id);
    }

}
