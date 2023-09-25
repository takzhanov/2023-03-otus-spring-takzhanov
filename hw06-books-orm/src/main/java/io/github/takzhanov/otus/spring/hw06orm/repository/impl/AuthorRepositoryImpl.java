package io.github.takzhanov.otus.spring.hw06orm.repository.impl;

import io.github.takzhanov.otus.spring.hw06orm.domain.Author;
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
        return em.createQuery("SELECT a FROM Author a", Author.class).getResultList();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public Optional<Author> findByName(String name) {
        try {
            var author = em.createQuery("SELECT a FROM Author a " +
                                        "WHERE a.name = :name", Author.class)
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
    public void delete(Long id) {
        Author author = em.find(Author.class, id);
        if (author != null) {
            em.remove(author);
        }
    }

    @Override
    public void forceDelete(Long id) {
        em.createNativeQuery("DELETE FROM book_author WHERE author_id = :authorId")
                .setParameter("authorId", id)
                .executeUpdate();
        delete(id);
    }
}
