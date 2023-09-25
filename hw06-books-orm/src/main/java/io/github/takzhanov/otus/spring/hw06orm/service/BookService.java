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

    Optional<Book> findById(Long id);

    Book create(BookCreateRequest book);

    Book update(Book updatedBook);

    Book update(BookUpdateRequest updateRequest);

    Book patch(BookPatchRequest patchRequest);

    void delete(Long id);

    Comment addCommentToBook(long id, String commentText);

    Author addAuthorToBook(long bookId, long authorId);

    Author removeAuthor(long bookId, long authorId);

    Genre addGenre(long id, long genreId);

    Genre removeGenre(long id, long genreId);
}
