package io.github.takzhanov.otus.spring.hw06orm.repository.impl;

import io.github.takzhanov.otus.spring.hw06orm.domain.Book;
import io.github.takzhanov.otus.spring.hw06orm.domain.Genre;
import io.github.takzhanov.otus.spring.hw06orm.exception.GenreNotFoundException;
import io.github.takzhanov.otus.spring.hw06orm.repository.GenreRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryImpl implements GenreRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Genre> findAll() {
        var query = em.createQuery("SELECT g FROM Genre g", Genre.class);
        return query.getResultList();
    }

    @Override
    public Optional<Genre> findById(long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public Genre getById(long id) {
        return findById(id).orElseThrow(() -> new GenreNotFoundException(id));
    }

    @Override
    public Optional<Genre> findByName(String name) {
        try {
            var genre = em.createQuery(
                            "SELECT g FROM Genre g WHERE g.name = :name", Genre.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return Optional.ofNullable(genre);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Genre save(Genre genre) {
        if (genre.getId() == null) {
            em.persist(genre);
            return genre;
        } else {
            return em.merge(genre);
        }
    }

    @Override
    public void delete(long id) {
        var genre = em.find(Genre.class, id);
        if (genre != null) {
            em.remove(genre);
        }
    }

    @Override
    public void forceDelete(long id) {
        var genre = em.find(Genre.class, id);
        if (genre != null) {
            var associatedBooks = em.createQuery(
                            "SELECT b FROM Book b JOIN b.genres g WHERE g.id = :genreId", Book.class)
                    .setParameter("genreId", id)
                    .getResultList();

            for (var book : associatedBooks) {
                book.getGenres().remove(genre);
            }

            em.remove(genre);
        }
    }
}
