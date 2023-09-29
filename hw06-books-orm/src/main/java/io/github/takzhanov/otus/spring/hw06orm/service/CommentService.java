package io.github.takzhanov.otus.spring.hw06orm.service;

import io.github.takzhanov.otus.spring.hw06orm.domain.Comment;
import java.util.List;
import java.util.Optional;

public interface CommentService {
    List<Comment> findAll();

    Optional<Comment> findById(long id);

    Comment getById(long id);

    Comment create(Comment comment);

    Comment update(Comment comment);

    void delete(long commentId);
}
