package io.github.takzhanov.otus.spring.hw06orm.repository;

import io.github.takzhanov.otus.spring.hw06orm.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
