package io.github.takzhanov.otus.spring.hw06orm.service;

import io.github.takzhanov.otus.spring.hw06orm.domain.Author;
import io.github.takzhanov.otus.spring.hw06orm.domain.Book;
import io.github.takzhanov.otus.spring.hw06orm.domain.Comment;
import io.github.takzhanov.otus.spring.hw06orm.domain.Genre;
import io.github.takzhanov.otus.spring.hw06orm.service.dto.BookCreateRequest;
import io.github.takzhanov.otus.spring.hw06orm.service.dto.BookPatchRequest;
import io.github.takzhanov.otus.spring.hw06orm.service.dto.BookUpdateRequest;
import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findAll();

    Optional<Book> findById(long id);

    Book getById(long id);

    List<Comment> getCommentsByBookId(long bookId);

    Book save(Book book);

    Book create(BookCreateRequest book);

    Book update(Book updatedBook);

    Book update(BookUpdateRequest updateRequest);

    Book patch(BookPatchRequest patchRequest);

    void delete(long bookId);

    Comment addCommentToBook(long bookId, String text);

    Author addAuthorToBook(long bookId, long authorId);

    Author removeAuthorFromBook(long bookId, long authorId);

    Genre addGenreToBook(long bookId, long genreId);

    Genre removeGenreFromBook(long bookId, long genreId);
}
