package io.github.takzhanov.otus.spring.hw06orm.repository.impl;

import io.github.takzhanov.otus.spring.hw06orm.domain.Book;
import io.github.takzhanov.otus.spring.hw06orm.repository.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
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
    public Book findById(Long id) {
        return em.find(Book.class, id);
    }

    @Override
    public Book create(Book book) {
        em.persist(book);
        return book;
    }

    @Override
    public int update(Book book) {
        em.merge(book);
        return 1;
    }

    @Override
    public int delete(Long id) {
        Book book = em.find(Book.class, id);
        if (book != null) {
            em.remove(book);
            return 1;
        }
        return 0;
    }
}
