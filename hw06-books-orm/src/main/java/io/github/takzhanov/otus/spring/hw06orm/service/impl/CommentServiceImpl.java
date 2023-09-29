package io.github.takzhanov.otus.spring.hw06orm.service.impl;

import io.github.takzhanov.otus.spring.hw06orm.domain.Comment;
import io.github.takzhanov.otus.spring.hw06orm.repository.CommentRepository;
import io.github.takzhanov.otus.spring.hw06orm.service.CommentService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Comment> findById(long id) {
        return commentRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Comment getById(long id) {
        return commentRepository.getById(id);
    }

    @Override
    @Transactional
    public Comment create(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public Comment update(Comment updatedComment) {
        var comment = commentRepository.getById(updatedComment.getId());
        comment.setText(updatedComment.getText());
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void delete(long commentId) {
        commentRepository.delete(commentId);
    }

}
