package io.github.takzhanov.otus.spring.hw05.repository.impl;

import io.github.takzhanov.otus.spring.hw05.domain.Genre;
import io.github.takzhanov.otus.spring.hw05.repository.GenreRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class GenreRepositoryImpl implements GenreRepository {
    private final NamedParameterJdbcOperations jdbc;

    private final RowMapper<Genre> genreRowMapper = (rs, rowNum) -> {
        return new Genre(rs.getLong("id"), rs.getString("name"));
    };

    @Override
    public List<Genre> findAll() {
        var sql = "SELECT id, name FROM genre";
        return jdbc.query(sql, genreRowMapper);
    }

    @Override
    public Genre findById(Long id) {
        var sql = "SELECT id, name FROM genre WHERE id = :id";
        var params = new MapSqlParameterSource("id", id);
        return jdbc.query(sql, params, genreRowMapper).stream().findFirst().orElse(null);
    }

    @Override
    public Genre findByName(String name) {
        var sql = "SELECT id, name FROM genre WHERE name = :name";
        var params = new MapSqlParameterSource("name", name);
        return jdbc.query(sql, params, genreRowMapper).stream().findFirst().orElse(null);
    }

    @Override
    public Genre create(Genre genre) {
        var sql = "INSERT INTO genre (name) VALUES (:name)";
        var params = new MapSqlParameterSource("name", genre.getName());
        var keyHolder = new GeneratedKeyHolder();
        jdbc.update(sql, params, keyHolder, new String[]{"id"});
        return new Genre(keyHolder.getKey().longValue(), genre.getName());
    }

    @Override
    public int update(Genre genre) {
        var sql = "UPDATE genre SET name = :name WHERE id = :id";
        var params = new MapSqlParameterSource();
        params.addValue("name", genre.getName());
        params.addValue("id", genre.getId());
        return jdbc.update(sql, params);
    }

    @Override
    public int delete(Long id) {
        var sql = "DELETE FROM genre WHERE id = :id";
        return jdbc.update(sql, new MapSqlParameterSource("id", id));
    }

    @Override
    public int forceDelete(Long id) {
        var sql = "DELETE FROM book_genre WHERE genre_id = :id";
        jdbc.update(sql, new MapSqlParameterSource("id", id));

        return delete(id);
    }
}

