package io.github.takzhanov.otus.spring.hw06orm.repository.impl;

import io.github.takzhanov.otus.spring.hw06orm.domain.Author;
import io.github.takzhanov.otus.spring.hw06orm.domain.Book;
import io.github.takzhanov.otus.spring.hw06orm.repository.AuthorRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class AuthorRepositoryImpl implements AuthorRepositoryCustom {
    @PersistenceContext
    private final EntityManager em;

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
