package io.github.takzhanov.otus.spring.lesson07.repository.impl;

import io.github.takzhanov.otus.spring.lesson07.domain.Genre;
import io.github.takzhanov.otus.spring.lesson07.repository.GenreRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
        String sql = "SELECT * FROM genre";
        return jdbc.query(sql, genreRowMapper);
    }

    @Override
    public Genre findById(Long id) {
        String sql = "SELECT * FROM genre WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return jdbc.query(sql, params, genreRowMapper).stream().findFirst().orElse(null);
    }

    @Override
    public Genre findByName(String name) {
        String sql = "SELECT * FROM genre WHERE name = :name";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", name);
        return jdbc.query(sql, params, genreRowMapper).stream().findFirst().orElse(null);
    }

    @Override
    public Genre create(Genre genre) {
        String sql = "INSERT INTO genre (name) VALUES (:name)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", genre.getName());
        jdbc.update(sql, params, keyHolder, new String[]{"id"});
        return new Genre(keyHolder.getKey().longValue(), genre.getName());
    }

    @Override
    public int update(Genre genre) {
        String sql = "UPDATE genre SET name = :name WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("name", genre.getName());
        params.addValue("id", genre.getId());
        return jdbc.update(sql, params);
    }

    @Override
    public int delete(Long id) {
        String sql = "DELETE FROM genre WHERE id = :id";
        var params = new MapSqlParameterSource("id", id);
        return jdbc.update(sql, params);
    }

    @Override
    public int forceDelete(Long id) {
        var params = new MapSqlParameterSource("id", id);

        String sql = "DELETE FROM book_genre WHERE genre_id = :id";
        jdbc.update(sql, params);

        sql = "DELETE FROM genre WHERE id = :id";
        return jdbc.update(sql, params);
    }
}

