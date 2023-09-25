package io.github.takzhanov.otus.spring.hw06orm.repository.impl;

import io.github.takzhanov.otus.spring.hw06orm.domain.Book;
import io.github.takzhanov.otus.spring.hw06orm.repository.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Book> findAll() {
        return em.createQuery("SELECT b FROM Book b " +
                              "LEFT JOIN FETCH b.authors " +
                              "LEFT JOIN FETCH b.genres " +
                              "LEFT JOIN FETCH b.comments", Book.class)
                .getResultList();
    }

    @Override
    public Optional<Book> findById(Long id) {
        try {
            var book = em.createQuery("SELECT b FROM Book b " +
                                      "LEFT JOIN FETCH b.authors " +
                                      "LEFT JOIN FETCH b.genres " +
                                      "LEFT JOIN FETCH b.comments " +
                                      "WHERE b.id = :id", Book.class)
                    .setParameter("id", id)
                    .getSingleResult();
            return Optional.ofNullable(book);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            em.persist(book);
            return book;
        } else {
            return em.merge(book);
        }
    }

    @Override
    public void delete(Long id) {
        Book book = em.find(Book.class, id);
        if (book != null) {
            em.remove(book);
        }
    }
}
