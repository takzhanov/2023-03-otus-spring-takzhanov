package io.github.takzhanov.otus.spring.hw06orm.repository.impl;

import io.github.takzhanov.otus.spring.hw06orm.domain.Author;
import io.github.takzhanov.otus.spring.hw06orm.domain.Book;
import io.github.takzhanov.otus.spring.hw06orm.domain.Genre;
import io.github.takzhanov.otus.spring.hw06orm.repository.AuthorRepository;
import io.github.takzhanov.otus.spring.hw06orm.repository.BookRepository;
import io.github.takzhanov.otus.spring.hw06orm.repository.GenreRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import static java.util.function.Function.identity;

@Repository
@RequiredArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    private final NamedParameterJdbcOperations jdbc;

    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final RowMapper<Book> bookIdAndTitleRowMapper = (rs, rowNum) -> {
        var id = rs.getLong("id");
        var title = rs.getString("title");
        return new Book(id, title, new HashSet<>(), new HashSet<>());
    };

    private final RowMapper<Book> rawBookRowMapper = (rs, rowNum) -> {
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

    private final ResultSetExtractor<List<Book>> booksResultSetExtractor = rs -> {
        List<Book> flatBooks = new ArrayList<>();
        int rowNum = 0;
        while (rs.next()) {
            flatBooks.add(rawBookRowMapper.mapRow(rs, rowNum++));
        }
        return mergeBooks(flatBooks);
    };

    @Override
    public List<Book> findAll() {
        var books = findAllIdAndTitleOnly().stream().collect(Collectors.toMap(Book::getId, identity()));

        var authorList = authorRepository.findAll();
        var authorsById = authorList.stream()
                .collect(Collectors.toMap(Author::getId, identity()));
        var bookToAuthorLinks = findAllB2aLinks();
        for (var link : bookToAuthorLinks) {
            books.get(link.bookId).getAuthors().add(authorsById.get(link.authorId));
        }

        var genreList = genreRepository.findAll();
        var genresById = genreList.stream()
                .collect(Collectors.toMap(Genre::getId, identity()));
        var bookToGenreLinks = findAllB2gLinks();
        for (var link : bookToGenreLinks) {
            books.get(link.bookId).getGenres().add(genresById.get(link.genreId));
        }

        return books.values().stream().toList();
    }

    private List<Book> findAllIdAndTitleOnly() {
        var sql = "SELECT id, title FROM book b";
        return jdbc.query(sql, bookIdAndTitleRowMapper);
    }

    private List<B2aLink> findAllB2aLinks() {
        var sql = "SELECT book_id, author_id FROM book_author";
        return jdbc.query(sql, (rs, rowNum) -> new B2aLink(rs.getLong("book_id"), rs.getLong("author_id")));
    }

    private List<B2gLink> findAllB2gLinks() {
        var sql = "SELECT book_id, genre_id FROM book_genre";
        return jdbc.query(sql, (rs, rowNum) -> new B2gLink(rs.getLong("book_id"), rs.getLong("genre_id")));
    }

    @Override
    public Book findById(Long id) {
        var sql = """
                SELECT b.id,
                       b.title,
                       g.id   AS genre_id,
                       g.name AS genre_name,
                       a.id   AS author_id,
                       a.name AS author_name
                FROM book b
                         LEFT JOIN book_author ba ON b.id = ba.book_id
                         LEFT JOIN author a ON a.id = ba.author_id
                         LEFT JOIN book_genre bg ON b.id = bg.book_id
                         LEFT JOIN genre g ON g.id = bg.genre_id
                WHERE b.id = :id;
                """;

        var books = jdbc.query(sql, new MapSqlParameterSource("id", id), booksResultSetExtractor);
        return mergeBooks(books).stream().findFirst().orElse(null);
    }

    @Override
    public Book create(Book book) {
        var sql = "INSERT INTO book (title) VALUES (:title)";
        var params = new MapSqlParameterSource("title", book.getTitle());
        var keyHolder = new GeneratedKeyHolder();
        jdbc.update(sql, params, keyHolder, new String[]{"id"});
        final long bookId = keyHolder.getKey().longValue();

        insertBookToAuthor(bookId, book);
        insertBookToGenre(bookId, book);

        return new Book(bookId, book.getTitle(), book.getAuthors(), book.getGenres());
    }

    private void insertBookToAuthor(long bookId, Book book) {
        var sql = "INSERT INTO book_author (book_id, author_id) VALUES (:book_id, :author_id)";
        for (Author author : book.getAuthors()) {
            var params = new MapSqlParameterSource();
            params.addValue("book_id", bookId);
            params.addValue("author_id", author.getId());
            jdbc.update(sql, params);
        }
    }

    private void insertBookToGenre(long bookId, Book book) {
        var sql = "INSERT INTO book_genre (book_id, genre_id) VALUES (:book_id, :genre_id)";
        for (Genre genre : book.getGenres()) {
            var params = new MapSqlParameterSource();
            params.addValue("book_id", bookId);
            params.addValue("genre_id", genre.getId());
            jdbc.update(sql, params);
        }
    }

    @Override
    public int update(Book book) {
        var sql = "UPDATE book SET title = :title WHERE id = :id";
        var params = new MapSqlParameterSource();
        params.addValue("title", book.getTitle());
        params.addValue("id", book.getId());
        final int updatedRowCount = jdbc.update(sql, params);

        deleteBookToAuthor(book.getId());
        insertBookToAuthor(book.getId(), book);

        deleteBookToGenre(book.getId());
        insertBookToGenre(book.getId(), book);

        return updatedRowCount;
    }

    private void deleteBookToAuthor(long bookId) {
        var sql = "DELETE FROM book_author WHERE book_id = :id";
        jdbc.update(sql, new MapSqlParameterSource("id", bookId));
    }

    private void deleteBookToGenre(long bookId) {
        var sql = "DELETE FROM book_genre WHERE book_id = :id";
        jdbc.update(sql, new MapSqlParameterSource("id", bookId));
    }

    @Override
    public int delete(Long id) {
        var sql = "DELETE FROM book WHERE id = :id";
        return jdbc.update(sql, new MapSqlParameterSource("id", id));
    }

    private List<Book> mergeBooks(List<Book> flatBooks) {
        return new ArrayList<>(flatBooks.stream()
                .collect(Collectors.toMap(
                        Book::getId,
                        book -> new Book(book.getId(), book.getTitle(), new HashSet<>(book.getAuthors()),
                                new HashSet<>(book.getGenres())),
                        (book1, book2) -> {
                            book1.getAuthors().addAll(book2.getAuthors());
                            book1.getGenres().addAll(book2.getGenres());
                            return book1;
                        }
                )).values());
    }

    private record B2aLink(long bookId, long authorId) {
    }

    private record B2gLink(long bookId, long genreId) {
    }
}
