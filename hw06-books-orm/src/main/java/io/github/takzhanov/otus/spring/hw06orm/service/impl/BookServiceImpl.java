package io.github.takzhanov.otus.spring.hw06orm.service.impl;

import io.github.takzhanov.otus.spring.hw06orm.domain.Author;
import io.github.takzhanov.otus.spring.hw06orm.domain.Book;
import io.github.takzhanov.otus.spring.hw06orm.domain.Comment;
import io.github.takzhanov.otus.spring.hw06orm.domain.Genre;
import io.github.takzhanov.otus.spring.hw06orm.exception.BookNotFoundException;
import io.github.takzhanov.otus.spring.hw06orm.repository.BookRepository;
import io.github.takzhanov.otus.spring.hw06orm.repository.CommentRepository;
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

    private final CommentRepository commentRepository;

    private final BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findById(long id) {
        return bookRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Book getById(long id) {
        return findById(id).orElseThrow(() -> new BookNotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentsByBookId(long bookId) {
        return commentRepository.findAllByBookId(bookId);
    }

    @Override
    @Transactional
    public Book save(Book book) {
        return bookRepository.save(book);
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
        getById(updatedBook.getId());
        return bookRepository.save(updatedBook);
    }

    @Override
    @Transactional
    public Book update(BookUpdateRequest updateRequest) {
        getById(updateRequest.id());
        var newAuthors = authorService.findOrCreateByName(updateRequest.authorNames());
        var newGenres = genreService.findOrCreateByName(updateRequest.genreNames());
        var updatedBook = new Book(updateRequest.id(), updateRequest.title(), newAuthors, newGenres);
        return bookRepository.save(updatedBook);
    }

    @Override
    @Transactional
    public Book patch(BookPatchRequest patchRequest) {
        var oldBook = getById(patchRequest.id());

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
        return bookRepository.save(patchedBook);
    }

    @Override
    @Transactional
    public void delete(long bookId) {
        bookRepository.deleteById(bookId);
    }

    @Override
    @Transactional
    public Comment addCommentToBook(long bookId, String text) {
        var book = getById(bookId);

        var newComment = new Comment(text);
        book.addComment(newComment);

        return commentRepository.save(newComment);
    }

    @Override
    @Transactional
    public Author addAuthorToBook(long id, long authorId) {
        var book = getById(id);
        var author = authorService.getById(authorId);

        book.getAuthors().add(author);
        bookRepository.save(book);
        return author;
    }

    @Override
    @Transactional
    public Author removeAuthorFromBook(long id, long authorId) {
        var book = getById(id);
        var author = authorService.getById(authorId);

        book.getAuthors().remove(author);
        bookRepository.save(book);
        return author;
    }

    @Override
    @Transactional
    public Genre addGenreToBook(long bookId, long genreId) {
        var book = getById(bookId);
        var genre = genreService.getById(genreId);

        book.getGenres().add(genre);
        bookRepository.save(book);
        return genre;
    }

    @Override
    @Transactional
    public Genre removeGenreFromBook(long bookId, long genreId) {
        var book = getById(bookId);
        var genre = genreService.getById(genreId);

        book.getGenres().remove(genre);
        bookRepository.save(book);
        return genre;
    }

}

