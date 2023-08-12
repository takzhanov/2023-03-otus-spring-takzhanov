package io.github.takzhanov.otus.spring.lesson07.repository.impl;

import io.github.takzhanov.otus.spring.lesson07.domain.Author;
import io.github.takzhanov.otus.spring.lesson07.domain.Book;
import io.github.takzhanov.otus.spring.lesson07.domain.Genre;
import io.github.takzhanov.otus.spring.lesson07.repository.BookRepository;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    private final NamedParameterJdbcOperations jdbc;
    private final RowMapper<Book> bookRowMapper = (rs, rowNum) -> {
        Long id = rs.getLong("id");
        String title = rs.getString("title");

        Long authorId = rs.getObject("author_id", Long.class);
        String authorName = rs.getString("author_name");

        Long genreId = rs.getObject("genre_id", Long.class);
        String genreName = rs.getString("genre_name");

        Set<Author> authors = (authorId != null && authorName != null)
                ? Set.of(new Author(authorId, authorName))
                : Collections.emptySet();
        Set<Genre> genres = (genreId != null && genreName != null)
                ? Set.of(new Genre(genreId, genreName))
                : Collections.emptySet();

        return new Book(id, title, authors, genres);
    };

    @Override
    public List<Book> findAll() {
        String sql = """
                SELECT b.*, a.id AS author_id, a.name AS author_name, g.id AS genre_id, g.name AS genre_name
                FROM book b
                LEFT JOIN book_author ba ON b.id = ba.book_id
                LEFT JOIN author a ON a.id = ba.author_id
                LEFT JOIN book_genre bg ON b.id = bg.book_id
                LEFT JOIN genre g ON g.id = bg.genre_id;
                """;
        List<Book> booksWithRelations = jdbc.query(sql, bookRowMapper);

        return mergeBooks(booksWithRelations);
    }

    @Override
    public Book findById(Long id) {
        String sql = """
                SELECT b.*, g.id AS genre_id, g.name AS genre_name, a.id AS author_id, a.name AS author_name
                FROM book b
                LEFT JOIN book_author ba ON b.id = ba.book_id
                LEFT JOIN author a ON a.id = ba.author_id
                LEFT JOIN book_genre bg ON b.id = bg.book_id
                LEFT JOIN genre g ON g.id = bg.genre_id
                WHERE b.id = :id;
                """;
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        var books = jdbc.query(sql, params, bookRowMapper);
        return mergeBooks(books).stream().findFirst().orElse(null);
    }

    @Override
    public Book create(Book book) {
        String sql = "INSERT INTO book (title) VALUES (:title)";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(sql, params, keyHolder, new String[]{"id"});
        final long bookId = keyHolder.getKey().longValue();

        sql = "INSERT INTO book_author (book_id, author_id) VALUES (:book_id, :author_id)";
        for (Author author : book.getAuthors()) {
            params = new MapSqlParameterSource();
            params.addValue("book_id", bookId);
            params.addValue("author_id", author.getId());
            jdbc.update(sql, params);
        }

        sql = "INSERT INTO book_genre (book_id, genre_id) VALUES (:book_id, :genre_id)";
        for (Genre genre : book.getGenres()) {
            params = new MapSqlParameterSource();
            params.addValue("book_id", bookId);
            params.addValue("genre_id", genre.getId());
            jdbc.update(sql, params);
        }

        return new Book(bookId, book.getTitle(), book.getAuthors(), book.getGenres());
    }

    @Override
    public int update(Book book) {
        String sql = "UPDATE book SET title = :title WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        params.addValue("id", book.getId());
        final int updatedRowCount = jdbc.update(sql, params);

        // Update authors
        sql = "DELETE FROM book_author WHERE book_id = :id";
        jdbc.update(sql, params);

        sql = "INSERT INTO book_author (book_id, author_id) VALUES (:book_id, :author_id)";
        for (Author author : book.getAuthors()) {
            var b2aParams = new MapSqlParameterSource();
            b2aParams.addValue("book_id", book.getId());
            b2aParams.addValue("author_id", author.getId());
            jdbc.update(sql, b2aParams);
        }

        // Update genres
        sql = "DELETE FROM book_genre WHERE book_id = :id";
        jdbc.update(sql, params);

        sql = "INSERT INTO book_genre (book_id, genre_id) VALUES (:book_id, :genre_id)";
        for (Genre genre : book.getGenres()) {
            var g2aParams = new MapSqlParameterSource();
            g2aParams.addValue("book_id", book.getId());
            g2aParams.addValue("genre_id", genre.getId());
            jdbc.update(sql, g2aParams);
        }

        return updatedRowCount;
    }

    @Override
    public int delete(Long id) {
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);

        String sql = "DELETE FROM book_author WHERE book_id = :id";
        jdbc.update(sql, params);

        sql = "DELETE FROM book_genre WHERE book_id = :id";
        jdbc.update(sql, params);

        sql = "DELETE FROM book WHERE id = :id";
        return jdbc.update(sql, params);
    }

    private List<Book> mergeBooks(List<Book> flatBooks) {
        return new ArrayList<>(flatBooks.stream()
                .collect(Collectors.toMap(
                        Book::getId,
                        book -> new Book(book.getId(), book.getTitle(), new HashSet<>(book.getAuthors()), new HashSet<>(book.getGenres())),
                        (book1, book2) -> {
                            book1.getAuthors().addAll(book2.getAuthors());
                            book1.getGenres().addAll(book2.getGenres());
                            return book1;
                        }
                )).values());
    }
}
