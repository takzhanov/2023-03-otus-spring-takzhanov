package io.github.takzhanov.otus.spring.hw06orm.repository.impl;

import io.github.takzhanov.otus.spring.hw06orm.domain.Book;
import io.github.takzhanov.otus.spring.hw06orm.repository.BookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.HashMap;
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
        var entityGraph = em.getEntityGraph("Book.authors,genres");
        return em.createQuery("SELECT b FROM Book b", Book.class)
                .setHint("jakarta.persistence.loadgraph", entityGraph)
                .getResultList();
    }

    @Override
    public Optional<Book> findById(long id) {
        var entityGraph = em.getEntityGraph("Book.authors,genres");
        var hints = new HashMap<String, Object>();
        hints.put("jakarta.persistence.loadgraph", entityGraph);
        return Optional.ofNullable(em.find(Book.class, id, hints));
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
    public void delete(long id) {
        Book book = em.find(Book.class, id);
        if (book != null) {
            em.remove(book);
        }
    }
}
