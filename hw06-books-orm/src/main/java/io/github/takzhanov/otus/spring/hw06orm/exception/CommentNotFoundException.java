package io.github.takzhanov.otus.spring.hw06orm.exception;

public class CommentNotFoundException extends AbstractEntityNotFoundException {

    public CommentNotFoundException(long id) {
        super("Comment with id %d is not found".formatted(id));
    }
}
