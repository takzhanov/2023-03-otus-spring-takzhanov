package io.github.takzhanov.otus.spring.hw06orm.repository.impl;

import io.github.takzhanov.otus.spring.hw06orm.domain.Genre;
import io.github.takzhanov.otus.spring.hw06orm.repository.GenreRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
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
        TypedQuery<Genre> query = em.createQuery("SELECT g FROM Genre g", Genre.class);
        return query.getResultList();
    }

    @Override
    public Optional<Genre> findById(Long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public Optional<Genre> findByName(String name) {
        try {
            var genre = em.createQuery("SELECT g FROM Genre g " +
                                       "WHERE g.name = :name", Genre.class)
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
    public int delete(Long id) {
        Genre genre = em.find(Genre.class, id);
        if (genre != null) {
            em.remove(genre);
            return 1;
        }
        return 0;
    }

    @Override
    public int forceDelete(Long id) {
        // First, manually delete relations in the book_genre table.
        em.createNativeQuery("DELETE FROM book_genre WHERE genre_id = :genreId")
                .setParameter("genreId", id)
                .executeUpdate();

        // Then delete the genre.
        return delete(id);
    }
}
