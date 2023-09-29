package io.github.takzhanov.otus.spring.hw06orm.repository.impl;

import io.github.takzhanov.otus.spring.hw06orm.domain.Author;
import io.github.takzhanov.otus.spring.hw06orm.domain.Book;
import io.github.takzhanov.otus.spring.hw06orm.exception.AuthorNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import(AuthorRepositoryImpl.class)
class AuthorRepositoryImplTest {
    @Autowired
    private AuthorRepositoryImpl authorRepository;

    @Autowired
    private TestEntityManager em;

    private static final long NON_EXISTENT_ID = -1L;

    @Test
    void findAll() {
        var newAuthor1 = new Author("Author 1");
        var newAuthor2 = new Author("Author 2");
        assertThat(authorRepository.findAll()).doesNotContain(newAuthor1, newAuthor2);
        em.persist(newAuthor1);
        em.persist(newAuthor2);

        assertThat(authorRepository.findAll()).contains(newAuthor1, newAuthor2);
    }

    @Test
    void findById_found() {
        var newAuthor = new Author("Author 1");
        em.persist(newAuthor);

        assertThat(authorRepository.findById(newAuthor.getId()))
                .isPresent()
                .contains(newAuthor);
    }

    @Test
    void findById_notFound() {
        assertThat(authorRepository.findById(NON_EXISTENT_ID))
                .isEmpty();
    }

    @Test
    void getById_found() {
        var newAuthor = new Author("Author 1");
        em.persist(newAuthor);

        assertThat(authorRepository.getById(newAuthor.getId()))
                .isEqualTo(newAuthor);
    }

    @Test
    void getById_notFound() {
        assertThatThrownBy(() -> authorRepository.getById(NON_EXISTENT_ID))
                .isInstanceOf(AuthorNotFoundException.class);
    }

    @Test
    void findByName_found() {
        var newAuthor = new Author("Author 1");
        em.persist(newAuthor);

        var foundAuthor = authorRepository.findByName(newAuthor.getName());
        assertThat(foundAuthor).isPresent().contains(newAuthor);
    }

    @Test
    void findByName_notFound() {
        var newAuthor = new Author("Author 1");
        assertThat(authorRepository.findByName(newAuthor.getName()))
                .isEmpty();
    }

    @Test
    void save_ok() {
        var newAuthor = new Author("Author 1");
        assertThat(newAuthor.getId()).isNull();
        authorRepository.save(newAuthor);
        assertThat(em.find(Author.class, newAuthor.getId())).isEqualTo(newAuthor);
    }

    @Test
    void save_updateNonExistentAuthor_ok() {
        var nonExistentAuthor = new Author("Ghost Author");
        nonExistentAuthor.setId(NON_EXISTENT_ID);

        var savedAuthor = authorRepository.save(nonExistentAuthor);

        assertThat(savedAuthor.getId())
                .isNotEqualTo(nonExistentAuthor.getId());
    }

    @Test
    void save_nonUniqName_throwException() {
        var UNIQ_NAME = "UNIQ_NAME";
        em.persist(new Author(UNIQ_NAME));

        assertThatThrownBy(() -> authorRepository.save(new Author(UNIQ_NAME)))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void save_update() {
        var newAuthor = new Author("Author 1");
        em.persist(newAuthor);
        assertThat(em.find(Author.class, newAuthor.getId())).isEqualTo(newAuthor);

        newAuthor.setName("Updated Test Author");
        authorRepository.save(newAuthor);

        Author updatedAuthor = em.find(Author.class, newAuthor.getId());
        assertThat(updatedAuthor.getName()).isEqualTo("Updated Test Author");
    }

    @Test
    void delete() {
        var newAuthor = new Author("Author 1");
        em.persist(newAuthor);
        assertThat(em.find(Author.class, newAuthor.getId())).isNotNull();

        authorRepository.delete(newAuthor.getId());

        assertThat(em.find(Author.class, newAuthor.getId())).isNull();
    }

    @Test
    void delete_nonExistent_ok() {
        authorRepository.delete(NON_EXISTENT_ID);
    }

    @Test
    void delete_withConstraint_throwException() {
        Author newAuthor = new Author("Author 1");
        Book book = new Book("Test Book");
        book.getAuthors().add(newAuthor);
        em.persist(book);
        //немного магии, чтобы далее сработал delete
        em.flush();
        em.clear();

        assertThatThrownBy(() -> {
            authorRepository.delete(newAuthor.getId());
            em.flush();
        }).isInstanceOf(ConstraintViolationException.class);

        em.clear();
        assertThat(em.find(Author.class, newAuthor.getId())).isNotNull();
    }

    @Test
    void forceDelete_ok() {
        var newAuthor = new Author("Author 1");
        Book book = new Book("Test Book");
        book.getAuthors().add(newAuthor);
        em.persist(book);

        authorRepository.forceDelete(newAuthor.getId());

        assertThat(em.find(Author.class, newAuthor.getId())).isNull();
        assertThat(em.find(Book.class, book.getId()).getAuthors()).doesNotContain(newAuthor);
    }
}