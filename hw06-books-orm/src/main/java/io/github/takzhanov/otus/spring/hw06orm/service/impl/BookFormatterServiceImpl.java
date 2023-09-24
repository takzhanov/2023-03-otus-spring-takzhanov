package io.github.takzhanov.otus.spring.hw06orm.service.impl;

import io.github.takzhanov.otus.spring.hw06orm.domain.Book;
import io.github.takzhanov.otus.spring.hw06orm.service.AuthorFormatterService;
import io.github.takzhanov.otus.spring.hw06orm.service.BookFormatterService;
import io.github.takzhanov.otus.spring.hw06orm.service.GenreFormatterService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookFormatterServiceImpl implements BookFormatterService {
    private final AuthorFormatterService authorFormatterService;

    private final GenreFormatterService genreFormatterService;

    @Override
    public String formatBook(Book book) {
        if (book == null) {
            return "NULL";
        }
        String genres = book.getGenres() != null && !book.getGenres().isEmpty()
                ? book.getGenres().stream()
                .map(genreFormatterService::formatGenre)
                .collect(Collectors.joining(", "))
                : "Unknown";
        String authors = book.getAuthors() != null && !book.getAuthors().isEmpty()
                ? book.getAuthors().stream()
                .map(authorFormatterService::formatAuthor)
                .collect(Collectors.joining(", "))
                : "Unknown";
        return String.format("ID: %d\nTitle: %s\nGenre: %s\nAuthors: %s\n",
                book.getId(), book.getTitle(),
                genres,
                authors);
    }

    @Override
    public String formatBooks(List<Book> books) {
        return books.stream()
                .map(this::formatBook)
                .collect(Collectors.joining("\n"));
    }
}
