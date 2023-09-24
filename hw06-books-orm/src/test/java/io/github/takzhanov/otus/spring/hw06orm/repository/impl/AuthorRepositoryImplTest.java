package io.github.takzhanov.otus.spring.hw06orm.repository.impl;

import io.github.takzhanov.otus.spring.hw06orm.domain.Author;
import io.github.takzhanov.otus.spring.hw06orm.repository.AuthorRepository;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest
@Import(AuthorRepositoryImpl.class)
class AuthorRepositoryImplTest {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TestEntityManager em;

    private final Author ORWELL = new Author(1L, "Test Author 1");

    @Test
    void findAll() {
        var expectedNames = Set.of("Test Author 1", "Test Author 2", "Test Author 3");
        var actualNames = authorRepository.findAll().stream().map(Author::getName).collect(Collectors.toSet());
        assertThat(actualNames)
                .containsExactlyInAnyOrderElementsOf(expectedNames);
    }

    @Test
    void findById_found() {
        assertThat(authorRepository.findById(ORWELL.getId()))
                .contains(ORWELL);
    }

    @Test
    void findById_notFound() {
        assertThat(authorRepository.findById(-1L))
                .isEmpty();
    }

    @Test
    void findByName_found() {
        assertThat(authorRepository.findByName(ORWELL.getName()))
                .contains(ORWELL);
    }

    @Test
    void findByName_notFound() {
        final String authorName = "NOT_FOUND_NAME";
        assertThat(authorRepository.findByName(authorName))
                .isEmpty();
    }

    @Test
    void create_ok() {
        final String authorName = "CREATE_OK";
        assertThat(authorRepository.findByName(authorName))
                .isEmpty();

        var createdAuthor = authorRepository.create(new Author(authorName));

        assertThat(createdAuthor.getId()).isNotNull();
        assertThat(authorRepository.findByName(authorName))
                .contains(createdAuthor);
    }

    @Test
    void create_throwException() {
        final String authorName = "UNIQ_NAME";
        assertThat(authorRepository.create(new Author(authorName))).isNotNull();
        assertThatThrownBy(() -> authorRepository.create(new Author(authorName)))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void update() {
        var newAuthorName = "changed";
        final Author expected = new Author(1L, newAuthorName);
        assertThat(authorRepository.update(new Author(ORWELL.getId(), newAuthorName)))
                .isEqualTo(expected);
        assertThat(authorRepository.findById(ORWELL.getId()))
                .contains(expected);
    }

    @Test
    void update_throwException() {
        assertThatThrownBy(() -> {
            authorRepository.update(new Author(2L, ORWELL.getName()));
            em.flush();
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void delete() {
        assertThat(authorRepository.findById(3L)).isNotEmpty();
        authorRepository.delete(3L);
        assertThat(authorRepository.findById(3L)).isEmpty();
    }

    @Test
    void delete_throwException() {
        assertThat(authorRepository.findById(ORWELL.getId())).isNotEmpty();
        assertThatThrownBy(() -> {
            authorRepository.delete(ORWELL.getId());
            em.flush();
        }).isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void forceDelete() {
        assertThat(authorRepository.findById(ORWELL.getId())).isNotEmpty();
        authorRepository.forceDelete(ORWELL.getId());
        assertThat(authorRepository.findById(ORWELL.getId())).isEmpty();
    }
}