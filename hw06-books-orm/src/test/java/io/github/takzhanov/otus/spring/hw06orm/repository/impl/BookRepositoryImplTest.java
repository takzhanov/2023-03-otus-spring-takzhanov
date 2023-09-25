package io.github.takzhanov.otus.spring.hw06orm.repository.impl;

import io.github.takzhanov.otus.spring.hw06orm.domain.Author;
import io.github.takzhanov.otus.spring.hw06orm.domain.Book;
import io.github.takzhanov.otus.spring.hw06orm.domain.Comment;
import io.github.takzhanov.otus.spring.hw06orm.domain.Genre;
import io.github.takzhanov.otus.spring.hw06orm.repository.AuthorRepository;
import io.github.takzhanov.otus.spring.hw06orm.repository.BookRepository;
import io.github.takzhanov.otus.spring.hw06orm.repository.GenreRepository;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import({BookRepositoryImpl.class, AuthorRepositoryImpl.class, GenreRepositoryImpl.class})
class BookRepositoryImplTest {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void findById() {
        var expectedBook = new Book(1L, "1984",
                Set.of(new Author(1L, "Test Author 1")),
                Set.of(new Genre(1L, "Test Genre 1"), new Genre(2L, "Test Genre 2")),
                Set.of(new Comment(1L, "Comment 1"), new Comment(2L, "Comment 2")));

        var actualBook = bookRepository.findById(1L).get();

        assertThatBooksAreEquals(actualBook, expectedBook);
    }

    @Test
    void findAll_checkFullness() {
        var expectedTitles = Set.of("1984", "Harry Potter and the Philosopher's Stone");
        var actualTitles = bookRepository.findAll().stream().map(Book::getTitle).collect(Collectors.toSet());
        assertThat(actualTitles).containsAll(expectedTitles);
    }

    @Test
    void findAll_checkCorrectness() {
        for (var actual : bookRepository.findAll()) {
            var expected = bookRepository.findById(actual.getId()).get();
            assertThatBooksAreEquals(actual, expected);
        }
    }

    @Test
    void create() {
        var expectedBook = new Book("Test Book 1");
        final Author a2 = authorRepository.findById(2L).get();
        final Author a3 = authorRepository.findById(3L).get();
        expectedBook.getAuthors().addAll(Set.of(a2, a3));
        final Genre g1 = genreRepository.findById(1L).get();
        final Genre g3 = genreRepository.findById(3L).get();
        expectedBook.getGenres().addAll(Set.of(g1, g3));

        var createdBook = bookRepository.save(expectedBook);
        assertThat(createdBook.getId()).isNotNull();

        var foundBook = bookRepository.findById(createdBook.getId()).get();
        assertThat(foundBook)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedBook);
    }

    @Test
    void update() {
        var expectedBook = bookRepository.findById(1L).get();
        expectedBook.getAuthors().clear();
        expectedBook.getAuthors().add(new Author(3L, "Test Author 3"));
        expectedBook.getGenres().clear();
        expectedBook.getGenres().add(new Genre(2L, "Test Genre 2"));

        var savedBook = bookRepository.save(expectedBook);
        assertThat(savedBook.getId()).isEqualTo(1L);

        var foundBook = bookRepository.findById(1L).get();
        assertThatBooksAreEquals(foundBook, expectedBook);
    }

    @Test
    void delete() {
        assertThat(bookRepository.findById(1L)).isNotEmpty();
        bookRepository.delete(1L);
        assertThat(bookRepository.findById(1L)).isEmpty();
    }

    private void assertThatBooksAreEquals(Book actual, Book expected) {
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}