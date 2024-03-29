package io.github.takzhanov.otus.spring.hw05.service.impl;

import io.github.takzhanov.otus.spring.hw05.domain.Genre;
import io.github.takzhanov.otus.spring.hw05.exception.ConstraintException;
import io.github.takzhanov.otus.spring.hw05.exception.GenreAlreadyExistException;
import io.github.takzhanov.otus.spring.hw05.exception.GenreNotFoundException;
import io.github.takzhanov.otus.spring.hw05.repository.GenreRepository;
import io.github.takzhanov.otus.spring.hw05.service.GenreService;
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
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Genre findById(Long id) {
        return genreRepository.findById(id);
    }

    @Override
    @Transactional
    public Genre findOrCreateByName(String genreName) {
        var genre = genreRepository.findByName(genreName);
        return genre != null ? genre : genreRepository.create(new Genre(null, genreName));
    }

    @Override
    @Transactional
    public Set<Genre> findOrCreateByName(String[] genreNames) {
        if (genreNames == null) {
            return Collections.emptySet();
        }
        return Arrays.stream(genreNames)
                .map(String::trim)
                .filter(Predicate.not(String::isEmpty))
                .distinct()
                .map(this::findOrCreateByName)
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public Genre create(Genre newGenre) {
        return genreRepository.create(newGenre);
    }

    @Override
    @Transactional
    public Genre update(Genre updatedGenre) {
        try {
            int updatedRowCount = genreRepository.update(updatedGenre);
            if (updatedRowCount == 0) {
                throw new GenreNotFoundException(updatedGenre);
            }
            return updatedGenre;
        } catch (DuplicateKeyException e) {
            throw new GenreAlreadyExistException(updatedGenre);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            genreRepository.delete(id);
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintException(e);
        }
    }

    @Override
    @Transactional
    public void forceDelete(Long id) {
        genreRepository.forceDelete(id);
    }
}
