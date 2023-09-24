package io.github.takzhanov.otus.spring.hw06orm.service.impl;

import io.github.takzhanov.otus.spring.hw06orm.domain.Author;
import io.github.takzhanov.otus.spring.hw06orm.domain.Book;
import io.github.takzhanov.otus.spring.hw06orm.domain.Comment;
import io.github.takzhanov.otus.spring.hw06orm.domain.Genre;
import io.github.takzhanov.otus.spring.hw06orm.exception.BookNotFoundException;
import io.github.takzhanov.otus.spring.hw06orm.repository.BookRepository;
import io.github.takzhanov.otus.spring.hw06orm.service.AuthorService;
import io.github.takzhanov.otus.spring.hw06orm.service.BookService;
import io.github.takzhanov.otus.spring.hw06orm.service.GenreService;
import io.github.takzhanov.otus.spring.hw06orm.service.dto.BookCreateRequest;
import io.github.takzhanov.otus.spring.hw06orm.service.dto.BookPatchRequest;
import io.github.takzhanov.otus.spring.hw06orm.service.dto.BookUpdateRequest;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
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
    public BookDto findById(Long id) {
        var book = bookRepository.findById(id);
        return BookDto.fromBook(book);
    }

    @Override
    @Transactional
    public Book create(BookCreateRequest book) {
        Set<Author> authors = authorService.findOrCreateByName(book.authorNames());
        Set<Genre> genres = genreService.findOrCreateByName(book.genreNames());

        var newBook = new Book(null, book.title(), authors, genres);
        return bookRepository.create(newBook);
    }

    @Override
    @Transactional
    public Book update(Book updatedBook) {
        int updatedRowCount = bookRepository.update(updatedBook);
        if (updatedRowCount == 0) {
            throw new BookNotFoundException(updatedBook);
        }
        return updatedBook;
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
        var oldBook = bookRepository.findById(patchRequest.id());
        if (oldBook == null) {
            throw new BookNotFoundException(new Book(patchRequest.id(), null, null, null, null));
        }

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


    @Data
    @AllArgsConstructor
    public static class BookDto {
        private Long id;

        private String title;

        private Set<Author> authors;

        private Set<Genre> genres;

        private Set<Comment> comments;

        public static BookDto fromBook(Book book) {
            return new BookDto(book.getId(), book.getTitle(),
                    Set.copyOf(book.getAuthors()),
                    Set.copyOf(book.getGenres()),
                    Set.copyOf(book.getComments()));
        }

        public static Book toBook(BookDto book) {
            return new Book(book.getId(), book.getTitle(),
                    book.getAuthors(),
                    book.getGenres(),
                    book.getComments());
        }
    }
}
