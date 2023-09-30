package io.github.takzhanov.otus.spring.hw06orm.repository;

import io.github.takzhanov.otus.spring.hw06orm.domain.Author;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String name);
}
