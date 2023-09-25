package io.github.takzhanov.otus.spring.hw06orm.service.impl;

import io.github.takzhanov.otus.spring.hw06orm.domain.Author;
import io.github.takzhanov.otus.spring.hw06orm.domain.Book;
import io.github.takzhanov.otus.spring.hw06orm.domain.Comment;
import io.github.takzhanov.otus.spring.hw06orm.domain.Genre;
import io.github.takzhanov.otus.spring.hw06orm.exception.AuthorNotFoundException;
import io.github.takzhanov.otus.spring.hw06orm.exception.BookNotFoundException;
import io.github.takzhanov.otus.spring.hw06orm.exception.GenreNotFoundException;
import io.github.takzhanov.otus.spring.hw06orm.repository.BookRepository;
import io.github.takzhanov.otus.spring.hw06orm.service.AuthorService;
import io.github.takzhanov.otus.spring.hw06orm.service.BookService;
import io.github.takzhanov.otus.spring.hw06orm.service.GenreService;
import io.github.takzhanov.otus.spring.hw06orm.service.dto.BookCreateRequest;
import io.github.takzhanov.otus.spring.hw06orm.service.dto.BookPatchRequest;
import io.github.takzhanov.otus.spring.hw06orm.service.dto.BookUpdateRequest;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final AuthorService authorService;

    private final GenreService genreService;

    private final BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    @Transactional
    public Book create(BookCreateRequest book) {
        Set<Author> authors = authorService.findOrCreateByName(book.authorNames());
        Set<Genre> genres = genreService.findOrCreateByName(book.genreNames());

        var newBook = new Book(null, book.title(), authors, genres);
        return bookRepository.save(newBook);
    }

    @Override
    @Transactional
    public Book update(Book updatedBook) {
        bookRepository.findById(updatedBook.getId())
                .orElseThrow(() -> new BookNotFoundException(updatedBook.getId()));
        return bookRepository.save(updatedBook);
    }

    @Override
    @Transactional
    public Book update(BookUpdateRequest updateRequest) {
        var newAuthors = authorService.findOrCreateByName(updateRequest.authorNames());
        var newGenres = genreService.findOrCreateByName(updateRequest.genreNames());
        var updatedBook = new Book(updateRequest.id(), updateRequest.title(), newAuthors, newGenres);
        return update(updatedBook);
    }

    @Override
    @Transactional
    public Book patch(BookPatchRequest patchRequest) {
        var oldBook = bookRepository.findById(patchRequest.id())
                .orElseThrow(() -> new BookNotFoundException(patchRequest.id()));

        var newTitle = patchRequest.title() != null
                ? patchRequest.title()
                : oldBook.getTitle();
        var newAuthors = patchRequest.authorNames() != null
                ? authorService.findOrCreateByName(patchRequest.authorNames())
                : oldBook.getAuthors();
        var newGenres = patchRequest.genreNames() != null
                ? genreService.findOrCreateByName(patchRequest.genreNames())
                : oldBook.getGenres();

        var patchedBook = new Book(patchRequest.id(), newTitle, newAuthors, newGenres);
        return update(patchedBook);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        bookRepository.delete(id);
    }

    @Override
    @Transactional
    public Comment addCommentToBook(long id, String commentText) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        var newComment = new Comment(commentText);
        book.getComments().add(newComment);

        bookRepository.save(book);
        return newComment;
    }

    @Override
    @Transactional
    public Author addAuthorToBook(long id, long authorId) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        var author = authorService.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException(authorId));

        book.getAuthors().add(author);
        bookRepository.save(book);
        return author;
    }

    @Override
    @Transactional
    public Author removeAuthor(long id, long authorId) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        var author = authorService.findById(authorId)
                .orElseThrow(() -> new AuthorNotFoundException(authorId));

        book.getAuthors().remove(author);
        bookRepository.save(book);
        return author;
    }

    @Override
    @Transactional
    public Genre addGenre(long id, long genreId) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        var genre = genreService.findById(genreId)
                .orElseThrow(() -> new GenreNotFoundException(genreId));

        book.getGenres().add(genre);
        bookRepository.save(book);
        return genre;
    }

    @Override
    @Transactional
    public Genre removeGenre(long id, long genreId) {
        var book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));

        var genre = genreService.findById(genreId)
                .orElseThrow(() -> new GenreNotFoundException(genreId));

        book.getGenres().remove(genre);
        bookRepository.save(book);
        return genre;
    }

}

