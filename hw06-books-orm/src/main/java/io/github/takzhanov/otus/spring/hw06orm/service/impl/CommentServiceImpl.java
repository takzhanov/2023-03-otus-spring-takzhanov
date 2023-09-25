package io.github.takzhanov.otus.spring.hw06orm.service.impl;

import io.github.takzhanov.otus.spring.hw06orm.domain.Comment;
import io.github.takzhanov.otus.spring.hw06orm.exception.CommentNotFoundException;
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
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    @Transactional
    public Comment create(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public Comment update(Comment updatedComment) {
        var commentId = updatedComment.getId();
        var comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
        comment.setText(updatedComment.getText());
        return commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void delete(long commentId) {
        commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentNotFoundException(commentId));
        commentRepository.delete(commentId);
    }

}
