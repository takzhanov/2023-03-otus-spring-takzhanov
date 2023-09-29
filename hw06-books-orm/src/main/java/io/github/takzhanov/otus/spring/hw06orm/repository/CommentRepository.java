package io.github.takzhanov.otus.spring.hw06orm.repository;

import io.github.takzhanov.otus.spring.hw06orm.domain.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBookId(long bookId);
}
