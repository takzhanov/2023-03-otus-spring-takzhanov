package io.github.takzhanov.otus.spring.hw06orm.repository;

import io.github.takzhanov.otus.spring.hw06orm.domain.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentRepository {
    List<Comment> findAll();

    List<Comment> findAllByBookId(long bookId);

    Optional<Comment> findById(long id);

    Comment getById(long id);

    Comment save(Comment comment);

    void delete(long id);
}
