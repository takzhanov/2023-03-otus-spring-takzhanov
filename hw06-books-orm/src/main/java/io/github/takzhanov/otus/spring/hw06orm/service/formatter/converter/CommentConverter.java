package io.github.takzhanov.otus.spring.hw06orm.service.formatter.converter;

import io.github.takzhanov.otus.spring.hw06orm.domain.Comment;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter implements Converter<Comment, String> {
    @Override
    public String convert(Comment comment) {
        if (comment == null) {
            return "NULL";
        }
        return String.format("[ID: %d, Text: %s]", comment.getId(), comment.getText());
    }
}
