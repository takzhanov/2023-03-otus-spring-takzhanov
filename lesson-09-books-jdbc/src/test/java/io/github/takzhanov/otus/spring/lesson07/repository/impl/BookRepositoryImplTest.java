package io.github.takzhanov.otus.spring.lesson07.repository.impl;

import io.github.takzhanov.otus.spring.lesson07.domain.Author;
import io.github.takzhanov.otus.spring.lesson07.domain.Book;
import io.github.takzhanov.otus.spring.lesson07.domain.Genre;
import io.github.takzhanov.otus.spring.lesson07.repository.BookRepository;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@Import({BookRepositoryImpl.class, AuthorRepositoryImpl.class, GenreRepositoryImpl.class})
class BookRepositoryImplTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    void findById() {
        var expectedBook = new Book(1L, "1984",
                Set.of(new Author(1L, "George Orwell")),
                Set.of(new Genre(1L, "Fiction"), new Genre(2L, "Non-Fiction")));

        var actualBook = bookRepository.findById(1L);

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
            var expected = bookRepository.findById(actual.getId());
            assertThatBooksAreEquals(actual, expected);
        }
    }

    @Test
    void create() {
        var expectedBook = new Book(null, "NEW_BOOK",
                Set.of(new Author(3L, "Ernest Hemingway"), new Author(4L, "Pushkin")),
                Set.of(new Genre(3L, "Doc"), new Genre(4L, "Science")));

        var createdBook = bookRepository.create(expectedBook);
        assertThat(createdBook.getId()).isNotNull();
        var foundBook = bookRepository.findById(createdBook.getId());

        assertThat(foundBook)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedBook);
    }

    @Test
    void update() {
        var expectedBook = new Book(1L, "4891",
                Set.of(new Author(4L, "Pushkin")),
                Set.of(new Genre(2L, "Non-Fiction")));

        bookRepository.update(expectedBook);
        var foundBook = bookRepository.findById(1L);

        assertThatBooksAreEquals(foundBook, expectedBook);
    }

    @Test
    void delete() {
        assertThat(bookRepository.findById(1L)).isNotNull();
        assertThat(bookRepository.delete(1L)).isEqualTo(1);
        assertThat(bookRepository.findById(1L)).isNull();
    }

    private void assertThatBooksAreEquals(Book actual, Book expected) {
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}