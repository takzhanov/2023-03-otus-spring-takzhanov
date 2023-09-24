package io.github.takzhanov.otus.spring.hw05.service.impl;

import io.github.takzhanov.otus.spring.hw05.domain.Author;
import io.github.takzhanov.otus.spring.hw05.domain.Book;
import io.github.takzhanov.otus.spring.hw05.domain.Genre;
import io.github.takzhanov.otus.spring.hw05.exception.BookNotFoundException;
import io.github.takzhanov.otus.spring.hw05.repository.BookRepository;
import io.github.takzhanov.otus.spring.hw05.service.AuthorService;
import io.github.takzhanov.otus.spring.hw05.service.GenreService;
import io.github.takzhanov.otus.spring.hw05.service.dto.BookCreateRequest;
import io.github.takzhanov.otus.spring.hw05.service.dto.BookPatchRequest;
import java.util.Collections;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookServiceImplTest {

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private BookServiceImpl bookService;

    @Test
    void testCreate_titleOnly() {
        var expectedTitle = "Mock Book";
        var createRequest = new BookCreateRequest(expectedTitle, null, null);

        bookService.create(createRequest);

        verify(authorService, never()).findOrCreateByName(anyString());
        verify(genreService, never()).findOrCreateByName(anyString());
        var expectedBook = new Book(null, expectedTitle, Collections.emptySet(), Collections.emptySet());
        verify(bookRepository, only()).create(eq(expectedBook));
    }

    @Test
    public void testCreate_withAuthorAndGenre() {
        var author = new Author(1L, "Author1");
        var genre = new Genre(1L, "Genre1");
        var book = new Book(null, "Sample Book", Set.of(author), Set.of(genre));
        BookCreateRequest request = new BookCreateRequest(book.getTitle(),
                new String[]{author.getName()},
                new String[]{genre.getName()});

        when(authorService.findOrCreateByName(any(String[].class))).thenReturn(Set.of(author));
        when(genreService.findOrCreateByName(any(String[].class))).thenReturn(Set.of(genre));
        Book expected = mock(Book.class);
        when(bookRepository.create(any())).thenReturn(expected);

        final Book actual = bookService.create(request);

        verify(authorService, only()).findOrCreateByName(any(String[].class));
        verify(genreService, only()).findOrCreateByName(any(String[].class));
        verify(bookRepository, only()).create(eq(book));
        assertEquals(expected, actual);
    }

    @Test
    public void testPatch() {
        var author = new Author(1L, "Author1");
        var genre = new Genre(1L, "Genre1");
        var oldTitle = "Old Title";
        var oldBook = new Book(1L, oldTitle, Collections.emptySet(), Set.of(genre));
        var updatedTitle = "Updated Title";
        var patchRequest = new BookPatchRequest(1L, updatedTitle,
                new String[]{author.getName()},
                null);
        var expected = new Book(1L, updatedTitle, Set.of(author), Set.of(genre));

        when(bookRepository.findById(1L)).thenReturn(oldBook);
        when(authorService.findOrCreateByName(any(String[].class))).thenReturn(Set.of(author));
        when(bookRepository.update(any())).thenReturn(1);

        Book actual = bookService.patch(patchRequest);

        verify(bookRepository, times(1)).findById(eq(1L));
        verify(bookRepository, times(1)).update(eq(expected));
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void testPatch_NotFound() {
        BookPatchRequest patchRequest = new BookPatchRequest(1L, "Updated Title", null, null);
        when(bookRepository.findById(1L)).thenReturn(null);

        assertThrows(BookNotFoundException.class, () -> bookService.patch(patchRequest));
    }
}