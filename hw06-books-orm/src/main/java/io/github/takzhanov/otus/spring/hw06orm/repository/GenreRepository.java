package io.github.takzhanov.otus.spring.hw06orm.repository;

import io.github.takzhanov.otus.spring.hw06orm.domain.Genre;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long>, GenreRepositoryCustom {
    Optional<Genre> findByName(String name);
}
