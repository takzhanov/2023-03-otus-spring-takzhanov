package io.github.takzhanov.otus.spring.lesson07.service.impl;

import io.github.takzhanov.otus.spring.lesson07.domain.Author;
import io.github.takzhanov.otus.spring.lesson07.domain.Book;
import io.github.takzhanov.otus.spring.lesson07.domain.Genre;
import io.github.takzhanov.otus.spring.lesson07.exception.BookNotFoundException;
import io.github.takzhanov.otus.spring.lesson07.repository.BookRepository;
import io.github.takzhanov.otus.spring.lesson07.service.AuthorService;
import io.github.takzhanov.otus.spring.lesson07.service.BookService;
import io.github.takzhanov.otus.spring.lesson07.service.GenreService;
import io.github.takzhanov.otus.spring.lesson07.service.dto.BookCreateRequest;
import io.github.takzhanov.otus.spring.lesson07.service.dto.BookPatchRequest;
import io.github.takzhanov.otus.spring.lesson07.service.dto.BookUpdateRequest;
import java.util.List;
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
    public Book findById(Long id) {
        return bookRepository.findById(id);
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
            throw new BookNotFoundException(new Book(patchRequest.id(), null, null, null));
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

}

