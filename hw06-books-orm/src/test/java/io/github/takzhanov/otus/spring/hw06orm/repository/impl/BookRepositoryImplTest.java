package io.github.takzhanov.otus.spring.hw06orm.repository.impl;

import io.github.takzhanov.otus.spring.hw06orm.domain.Author;
import io.github.takzhanov.otus.spring.hw06orm.domain.Book;
import io.github.takzhanov.otus.spring.hw06orm.domain.Comment;
import io.github.takzhanov.otus.spring.hw06orm.domain.Genre;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(BookRepositoryImpl.class)
class BookRepositoryImplTest {
    @Autowired
    private BookRepositoryImpl bookRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    void findAll() {
        var book = new Book("Title 1");
        book.getAuthors().add(new Author("Author 1"));
        book.getAuthors().add(new Author("Author 2"));
        book.getGenres().add(new Genre("Genre 1"));
        book.getGenres().add(new Genre("Genre 2"));
        book.addComment(new Comment("Comment 1"));
        book.addComment(new Comment("Comment 2"));
        book.addComment(new Comment("Comment 3"));

        em.persistAndFlush(book);
        em.clear();

        var foundBooks = bookRepository.findAll();
        assertThat(foundBooks).hasSizeGreaterThanOrEqualTo(3);

        var savedBook = foundBooks.stream().filter(b -> book.getId().equals(b.getId())).findFirst().get();
        assertThat(savedBook).usingRecursiveComparison().isEqualTo(book);

        for (Book b : foundBooks) {
            assertThat(b).usingRecursiveComparison().isEqualTo(em.find(Book.class, b.getId()));
        }
    }


    @Test
    void findById() {
        var book = new Book("Title 1");
        book.getAuthors().add(new Author("Author 1"));
        book.getAuthors().add(new Author("Author 2"));
        book.getGenres().add(new Genre("Genre 1"));
        book.getGenres().add(new Genre("Genre 2"));
        book.addComment(new Comment("Comment 1"));
        book.addComment(new Comment("Comment 2"));
        book.addComment(new Comment("Comment 3"));

        em.persistAndFlush(book);
        em.clear();

        var actualBook = bookRepository.findById(book.getId()).get();
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(book);
    }


    @Test
    void save() {
        var book = new Book("Title 1");
        book.getAuthors().add(new Author("Author 1"));
        book.getAuthors().add(new Author("Author 2"));
        book.getGenres().add(new Genre("Genre 1"));
        book.getGenres().add(new Genre("Genre 2"));
        book.addComment(new Comment("Comment 1"));
        book.addComment(new Comment("Comment 2"));
        book.addComment(new Comment("Comment 3"));

        bookRepository.save(book);
        em.flush();
        em.clear();

        var actualBook = em.find(Book.class, book.getId());
        assertThat(actualBook).usingRecursiveComparison().isEqualTo(book);
    }

    @Test
    void delete() {
        var book = new Book("Title 1");
        book.getAuthors().add(new Author("Author 1"));
        book.getAuthors().add(new Author("Author 2"));
        book.getGenres().add(new Genre("Genre 1"));
        book.getGenres().add(new Genre("Genre 2"));
        book.addComment(new Comment("Comment 1"));
        book.addComment(new Comment("Comment 2"));
        book.addComment(new Comment("Comment 3"));
        em.persistAndFlush(book);
        em.clear();

        bookRepository.delete(book.getId());

        var actualBook = em.find(Book.class, book.getId());
        assertThat(actualBook).isNull();
    }
}