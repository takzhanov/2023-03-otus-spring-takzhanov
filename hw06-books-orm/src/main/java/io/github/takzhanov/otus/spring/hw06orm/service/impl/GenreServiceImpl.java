package io.github.takzhanov.otus.spring.hw06orm.service.impl;

import io.github.takzhanov.otus.spring.hw06orm.domain.Genre;
import io.github.takzhanov.otus.spring.hw06orm.exception.ConstraintException;
import io.github.takzhanov.otus.spring.hw06orm.exception.GenreNotFoundException;
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
    public Optional<Genre> findById(Long id) {
        return genreRepository.findById(id);
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
        return genreRepository.save(newGenre);
    }

    @Override
    @Transactional
    public Genre update(Genre updatedGenre) {
        return genreRepository.findById(updatedGenre.getId()).stream()
                .peek(c -> c.setName(updatedGenre.getName()))
                .peek(c -> genreRepository.save(c))
                .findFirst()
                .orElseThrow(() -> new GenreNotFoundException(updatedGenre));
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
