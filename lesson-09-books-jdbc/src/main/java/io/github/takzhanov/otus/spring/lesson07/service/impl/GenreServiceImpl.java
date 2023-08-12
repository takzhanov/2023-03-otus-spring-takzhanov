package io.github.takzhanov.otus.spring.lesson07.service.impl;

import io.github.takzhanov.otus.spring.lesson07.domain.Genre;
import io.github.takzhanov.otus.spring.lesson07.exception.ConstraintException;
import io.github.takzhanov.otus.spring.lesson07.exception.EntityNotFoundException;
import io.github.takzhanov.otus.spring.lesson07.repository.GenreRepository;
import io.github.takzhanov.otus.spring.lesson07.service.GenreService;
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
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre findById(Long id) {
        return genreRepository.findById(id);
    }

    @Override
    public Genre findOrCreateByName(String genreName) {
        var genre = genreRepository.findByName(genreName);
        return genre != null ? genre : genreRepository.create(new Genre(null, genreName));
    }

    @Override
    public Set<Genre> findOrCreateByName(String[] genreNames) {
        if (genreNames == null) return Collections.emptySet();
        return Arrays.stream(genreNames)
                .map(String::trim)
                .filter(Predicate.not(String::isEmpty))
                .distinct()
                .map(this::findOrCreateByName)
                .collect(Collectors.toSet());
    }

    @Override
    public Genre create(Genre newGenre) {
        return genreRepository.create(newGenre);
    }

    @Override
    public Genre update(Genre updatedGenre) {
        int updatedRowCount = genreRepository.update(updatedGenre);
        if (updatedRowCount == 0) {
            throw new EntityNotFoundException();
        }
        return updatedGenre;
    }

    @Override
    public void delete(Long id) {
        try {
            genreRepository.delete(id);
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintException(e);
        }
    }

    @Override
    public void forceDelete(Long id) {
        genreRepository.forceDelete(id);
    }
}
