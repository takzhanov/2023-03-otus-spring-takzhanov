package io.github.takzhanov.otus.spring.hw05.repository.impl;

import io.github.takzhanov.otus.spring.hw05.domain.Author;
import io.github.takzhanov.otus.spring.hw05.repository.AuthorRepository;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
@Import(AuthorRepositoryImpl.class)
class AuthorRepositoryImplTest {
    @Autowired
    private AuthorRepository authorRepository;

    private final Author ORWELL = new Author(1L, "Test Author 1");

    @Test
    void findAll() {
        var expectedNames = Set.of("Test Author 1", "Test Author 2", "Test Author 3");
        var actualNames = authorRepository.findAll().stream().map(Author::getName).collect(Collectors.toSet());
        assertThat(actualNames).containsAll(expectedNames);
    }

    @Test
    void findById_found() {
        final Author foundAuthor = authorRepository.findById(ORWELL.getId());
        assertThat(foundAuthor).isEqualTo(ORWELL);
    }

    @Test
    void findById_notFound() {
        final Author foundAuthor = authorRepository.findById(-1L);
        assertThat(foundAuthor).isEqualTo(null);
    }

    @Test
    void findByName_found() {
        assertThat(authorRepository.findByName(ORWELL.getName())).isEqualTo(ORWELL);
    }

    @Test
    void findByName_notFound() {
        final String authorName = "NOT_FOUND_NAME";
        assertThat(authorRepository.findByName(authorName)).isNull();
    }

    @Test
    void create_ok() {
        final String authorName = "CREATE_OK";
        assertThat(authorRepository.findByName(authorName)).isNull();
        var createdAuthor = authorRepository.create(new Author(authorName));
        assertThat(createdAuthor.getId()).isNotNull();
        var foundAuthor = authorRepository.findByName(authorName);
        assertThat(foundAuthor).isEqualTo(createdAuthor);
    }

    @Test
    void create_throwException() {
        final String authorName = "UNIQ_NAME";
        assertThat(authorRepository.create(new Author(authorName))).isNotNull();
        assertThatThrownBy(() -> authorRepository.create(new Author(authorName)))
                .isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    void update() {
        var newAuthorName = "changed";
        assertThat(authorRepository.update(new Author(ORWELL.getId(), newAuthorName))).isEqualTo(1);
        assertThat(authorRepository.findById(ORWELL.getId())).isEqualTo(new Author(1L, newAuthorName));
    }

    @Test
    void update_throwException() {
        assertThatThrownBy(() -> authorRepository.update(new Author(2L, ORWELL.getName())))
                .isInstanceOf(DuplicateKeyException.class);
    }

    @Test
    void delete() {
        assertThat(authorRepository.findById(3L)).isNotNull();
        assertThat(authorRepository.delete(3L)).isEqualTo(1);
        assertThat(authorRepository.findById(3L)).isNull();
    }

    @Test
    void delete_throwException() {
        assertThat(authorRepository.findById(ORWELL.getId())).isNotNull();
        assertThatThrownBy(() -> authorRepository.delete(ORWELL.getId()))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void forceDelete() {
        assertThat(authorRepository.findById(ORWELL.getId())).isNotNull();
        assertThat(authorRepository.forceDelete(ORWELL.getId())).isEqualTo(1);
        assertThat(authorRepository.findById(ORWELL.getId())).isNull();
    }
}