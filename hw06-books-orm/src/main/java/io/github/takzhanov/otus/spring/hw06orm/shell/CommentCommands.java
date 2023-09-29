package io.github.takzhanov.otus.spring.hw06orm.shell;

import io.github.takzhanov.otus.spring.hw06orm.domain.Comment;
import io.github.takzhanov.otus.spring.hw06orm.service.CommentService;
import io.github.takzhanov.otus.spring.hw06orm.service.formatter.FormatterService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class CommentCommands {
    private final CommentService commentService;

    private final FormatterService formatterService;

    @ShellMethod(value = "List all comments | Find comment", key = {"lc", "list-comments"})
    public String listComments(@ShellOption(defaultValue = "") Long id) {
        if (id == null) {
            var comments = commentService.findAll();
            return formatterService.format(comments);
        } else {
            var comment = commentService.getById(id);
            return formatterService.format(comment);
        }
    }

    @ShellMethod(value = "Create a new comment", key = {"cc", "create-comment"})
    public String createComment(@ShellOption String text) {
        var savedComment = commentService.create(new Comment(text));
        return "Created comment: " + formatterService.format(savedComment);
    }

    @ShellMethod(value = "Update a comment", key = {"uc", "update-comment"})
    public String updateComment(@ShellOption Long id, @ShellOption String text) {
        if (text == null) {
            return "Missing mandatory option '--text'";
        }
        var newComment = new Comment(id, text);
        var savedComment = commentService.update(newComment);
        return "Updated comment: " + formatterService.format(savedComment);
    }

    @ShellMethod(value = "Delete a comment", key = {"dc", "delete-comment"})
    public String deleteComment(@ShellOption Long id) {
        commentService.delete(id);
        return "Comment deleted";
    }
}