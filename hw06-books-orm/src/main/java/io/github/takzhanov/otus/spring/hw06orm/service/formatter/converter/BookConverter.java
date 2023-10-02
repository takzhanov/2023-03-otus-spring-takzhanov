package io.github.takzhanov.otus.spring.hw06orm.service.formatter.converter;

import io.github.takzhanov.otus.spring.hw06orm.domain.Book;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookConverter implements Converter<Book, String> {
    private final AuthorConverter authorFormatterService;

    private final GenreConverter genreFormatterService;

    @Override
    public String convert(Book book) {
        if (book == null) {
            return "NULL";
        }
        String genres = book.getGenres() != null && !book.getGenres().isEmpty()
                ? book.getGenres().stream()
                .map(genreFormatterService::convert)
                .collect(Collectors.joining(",", "[", "]"))
                : "[]";
        String authors = book.getAuthors() != null && !book.getAuthors().isEmpty()
                ? book.getAuthors().stream()
                .map(authorFormatterService::convert)
                .collect(Collectors.joining(",", "[", "]"))
                : "[]";
        return String.format("[ID: %d\n Title: [%s]\n Genre: %s\n Authors: %s]",
                book.getId(), book.getTitle(),
                genres,
                authors);
    }

}
