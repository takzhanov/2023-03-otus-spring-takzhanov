package io.github.takzhanov.otus.spring.hw06orm.repository.impl;

import io.github.takzhanov.otus.spring.hw06orm.domain.Book;
import io.github.takzhanov.otus.spring.hw06orm.domain.Genre;
import io.github.takzhanov.otus.spring.hw06orm.repository.GenreRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryImpl implements GenreRepositoryCustom {
    @PersistenceContext
    private final EntityManager em;

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
