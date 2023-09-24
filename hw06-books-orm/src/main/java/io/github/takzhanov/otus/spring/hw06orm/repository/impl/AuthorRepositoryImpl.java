package io.github.takzhanov.otus.spring.hw06orm.repository.impl;

import io.github.takzhanov.otus.spring.hw06orm.domain.Author;
import io.github.takzhanov.otus.spring.hw06orm.repository.AuthorRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class AuthorRepositoryImpl implements AuthorRepository {
    private final NamedParameterJdbcOperations jdbc;

    private final RowMapper<Author> authorRowMapper = (rs, rowNum) -> {
        return new Author(rs.getLong("id"), rs.getString("name"));
    };

    @Override
    public List<Author> findAll() {
        var sql = "SELECT id, name FROM author";
        return jdbc.query(sql, authorRowMapper);
    }

    @Override
    public Author findById(Long id) {
        var sql = "SELECT id, name FROM author WHERE id = :id";
        var params = new MapSqlParameterSource("id", id);
        return jdbc.query(sql, params, authorRowMapper).stream().findFirst().orElse(null);
    }

    @Override
    public Author findByName(String name) {
        var sql = "SELECT id, name FROM author WHERE name = :name";
        var params = new MapSqlParameterSource("name", name);
        return jdbc.query(sql, params, authorRowMapper).stream().findFirst().orElse(null);
    }

    @Override
    public Author create(Author author) {
        var sql = "INSERT INTO author (name) VALUES (:name)";
        var params = new MapSqlParameterSource("name", author.getName());
        var keyHolder = new GeneratedKeyHolder();
        jdbc.update(sql, params, keyHolder, new String[]{"id"});
        return new Author(keyHolder.getKey().longValue(), author.getName());
    }

    @Override
    public int update(Author author) {
        var sql = "UPDATE author SET name = :name WHERE id = :id";
        var params = new MapSqlParameterSource();
        params.addValue("name", author.getName());
        params.addValue("id", author.getId());
        return jdbc.update(sql, params);
    }

    @Override
    public int delete(Long id) {
        var sql = "DELETE FROM author WHERE id = :id";
        var params = new MapSqlParameterSource("id", id);
        return jdbc.update(sql, params);
    }

    @Override
    public int forceDelete(Long id) {
        var sql = "DELETE FROM book_author WHERE author_id = :id";
        jdbc.update(sql, new MapSqlParameterSource("id", id));

        return delete(id);
    }
}
