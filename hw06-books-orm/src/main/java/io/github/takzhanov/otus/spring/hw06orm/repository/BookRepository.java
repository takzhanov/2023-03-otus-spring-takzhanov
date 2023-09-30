package io.github.takzhanov.otus.spring.hw06orm.repository;

import io.github.takzhanov.otus.spring.hw06orm.domain.Book;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Override
    @EntityGraph(attributePaths = {"authors", "genres"})
    List<Book> findAll();

    @Override
    @EntityGraph(attributePaths = {"authors", "genres"})
    Optional<Book> findById(Long aLong);
}
