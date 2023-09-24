package io.github.takzhanov.otus.spring.hw06orm.repository;

import io.github.takzhanov.otus.spring.hw06orm.domain.Comment;
import java.util.List;

public interface CommentRepository {
    Comment save(Comment comment);

    Comment findById(Long id);

    List<Comment> findAll();

    void delete(Long id);
}
