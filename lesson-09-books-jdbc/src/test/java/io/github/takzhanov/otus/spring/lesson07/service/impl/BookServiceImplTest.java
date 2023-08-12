package io.github.takzhanov.otus.spring.lesson07.service.impl;

import io.github.takzhanov.otus.spring.lesson07.domain.Author;
import io.github.takzhanov.otus.spring.lesson07.domain.Book;
import io.github.takzhanov.otus.spring.lesson07.domain.Genre;
import io.github.takzhanov.otus.spring.lesson07.exception.EntityNotFoundException;
import io.github.takzhanov.otus.spring.lesson07.repository.BookRepository;
import io.github.takzhanov.otus.spring.lesson07.service.AuthorService;
import io.github.takzhanov.otus.spring.lesson07.service.GenreService;
import io.github.takzhanov.otus.spring.lesson07.service.dto.BookCreateRequest;
import io.github.takzhanov.otus.spring.lesson07.service.dto.BookPatchRequest;
import java.util.Collections;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {

    @Mock
    private AuthorService authorService;

    @Mock
    private GenreService genreService;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
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

        assertThrows(EntityNotFoundException.class, () -> bookService.patch(patchRequest));
    }
}