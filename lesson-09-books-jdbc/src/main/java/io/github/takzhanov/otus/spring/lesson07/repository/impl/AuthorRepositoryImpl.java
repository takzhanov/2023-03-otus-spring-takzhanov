package io.github.takzhanov.otus.spring.lesson07.repository.impl;

import io.github.takzhanov.otus.spring.lesson07.domain.Author;
import io.github.takzhanov.otus.spring.lesson07.repository.AuthorRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
        String sql = "SELECT * FROM author";
        return jdbc.query(sql, authorRowMapper);
    }

    @Override
    public Author findById(Long id) {
        String sql = "SELECT * FROM author WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return jdbc.query(sql, params, authorRowMapper).stream().findFirst().orElse(null);
    }

    @Override
    public Author findByName(String name) {
        String sql = "SELECT * FROM author WHERE name = :name";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);
        return jdbc.query(sql, params, authorRowMapper).stream().findFirst().orElse(null);
    }

    @Override
    public Author create(Author author) {
        String sql = "INSERT INTO author (name) VALUES (:name)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", author.getName());
        jdbc.update(sql, params, keyHolder, new String[]{"id"});
        return new Author(keyHolder.getKey().longValue(), author.getName());
    }

    @Override
    public int update(Author author) {
        String sql = "UPDATE author SET name = :name WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", author.getName());
        params.addValue("id", author.getId());
        return jdbc.update(sql, params);
    }

    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM author WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return jdbc.update(sql, params);
    }

    @Override
    public int forceDelete(Long id) {
        var params = new MapSqlParameterSource("id", id);

        String sql = "DELETE FROM book_author WHERE author_id = :id";
        jdbc.update(sql, params);

        sql = "DELETE FROM author WHERE id = :id";
        return jdbc.update(sql, params);
    }
}