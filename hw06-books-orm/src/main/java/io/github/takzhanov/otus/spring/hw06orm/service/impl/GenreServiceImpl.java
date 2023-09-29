package io.github.takzhanov.otus.spring.hw06orm.service.impl;

import io.github.takzhanov.otus.spring.hw06orm.domain.Genre;
import io.github.takzhanov.otus.spring.hw06orm.exception.ConstraintException;
import io.github.takzhanov.otus.spring.hw06orm.repository.GenreRepository;
import io.github.takzhanov.otus.spring.hw06orm.service.GenreService;
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
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Genre> findById(long id) {
        return genreRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Genre getById(long id) {
        return genreRepository.getById(id);
    }

    @Override
    @Transactional
    public Genre findOrCreateByName(String genreName) {
        return genreRepository.findByName(genreName)
                .orElseGet(() -> genreRepository.save(new Genre(genreName)));
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
        try {
            return genreRepository.save(newGenre);
        } catch (DataIntegrityViolationException ex) {
            throw new ConstraintException("Genre with name '%s' already exists".formatted(newGenre.getName()));
        }
    }

    @Override
    @Transactional
    public Genre update(Genre updatedGenre) {
        var genre = getById(updatedGenre.getId());
        genre.setName(updatedGenre.getName());
        return genreRepository.save(genre);
    }

    @Override
    @Transactional
    public void delete(long id) {
        genreRepository.delete(id);
    }

    @Override
    @Transactional
    public void forceDelete(long id) {
        genreRepository.forceDelete(id);
    }
}
