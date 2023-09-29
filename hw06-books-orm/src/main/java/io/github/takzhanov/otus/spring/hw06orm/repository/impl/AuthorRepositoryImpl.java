package io.github.takzhanov.otus.spring.hw06orm.repository.impl;

import io.github.takzhanov.otus.spring.hw06orm.domain.Author;
import io.github.takzhanov.otus.spring.hw06orm.domain.Book;
import io.github.takzhanov.otus.spring.hw06orm.exception.AuthorNotFoundException;
import io.github.takzhanov.otus.spring.hw06orm.repository.AuthorRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class AuthorRepositoryImpl implements AuthorRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<Author> findAll() {
        var query = em.createQuery("SELECT a FROM Author a", Author.class);
        return query.getResultList();
    }

    @Override
    public Optional<Author> findById(long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public Author getById(long id) {
        return findById(id).orElseThrow(() -> new AuthorNotFoundException(id));
    }

    @Override
    public Optional<Author> findByName(String name) {
        try {
            var author = em.createQuery(
                            "SELECT a FROM Author a WHERE a.name = :name", Author.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return Optional.ofNullable(author);
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Author save(Author author) {
        if (author.getId() == null) {
            em.persist(author);
            return author;
        } else {
            return em.merge(author);
        }
    }

    @Override
    public void delete(long id) {
        var author = em.find(Author.class, id);
        if (author != null) {
            em.remove(author);
        }
    }

    @Override
    public void forceDelete(long id) {
        var author = em.find(Author.class, id);
        if (author != null) {
            var associatedBooks = em.createQuery(
                            "SELECT b FROM Book b JOIN b.authors a WHERE a.id = :authorId", Book.class)
                    .setParameter("authorId", id)
                    .getResultList();

            for (var book : associatedBooks) {
                if (!book.getAuthors().remove(author)) {
                    //TODO и возможно перенести на уровень сервиса весь метод
                    throw new RuntimeException("Something goes wrong");
                }
            }

            em.remove(author);
        }
    }
}
