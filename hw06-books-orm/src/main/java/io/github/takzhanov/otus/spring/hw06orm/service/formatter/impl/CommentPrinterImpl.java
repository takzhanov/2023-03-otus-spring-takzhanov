package io.github.takzhanov.otus.spring.hw06orm.service.formatter.impl;

import io.github.takzhanov.otus.spring.hw06orm.domain.Comment;
import java.util.Locale;
import org.springframework.format.Printer;
import org.springframework.stereotype.Service;

@Service
public class CommentPrinterImpl implements Printer<Comment> {
    @Override
    public String print(Comment comment, Locale locale) {
        if (comment == null) {
            return "NULL";
        }
        return String.format("[ID: %d, Text: %s]", comment.getId(), comment.getText());
    }
}
