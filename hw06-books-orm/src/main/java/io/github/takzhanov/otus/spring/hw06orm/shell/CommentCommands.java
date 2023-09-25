package io.github.takzhanov.otus.spring.hw06orm.shell;

import io.github.takzhanov.otus.spring.hw06orm.domain.Comment;
import io.github.takzhanov.otus.spring.hw06orm.exception.EntityNotFoundException;
import io.github.takzhanov.otus.spring.hw06orm.service.CommentService;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.format.Printer;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class CommentCommands {
    private final CommentService commentService;

    private final Printer<Comment> commentPrinter;

    @ShellMethod(value = "List all comments", key = {"lc", "list-comments"})
    public String listComments() {
        return commentService.findAll().stream()
                .map(this::formatComment)
                .collect(Collectors.joining(",\n"));
    }

    @ShellMethod(value = "Read comment", key = {"rc", "read-comment"})
    public String readComment(@ShellOption Long id) {
        var foundComment = commentService.findById(id);
        if (foundComment.isEmpty()) {
            return "Comment not found with id: " + id;
        }
        return "Comment with id = " + id + ":\n" + formatComment(foundComment.get());
    }

    @ShellMethod(value = "Create a new comment", key = {"cc", "create-comment"})
    public String createComment(@ShellOption String text) {
        Comment savedComment = commentService.create(new Comment(text));
        return "Created new comment: " + formatComment(savedComment);
    }

    @ShellMethod(value = "Update a comment", key = {"uc", "update-comment"})
    public String updateComment(@ShellOption Long id, @ShellOption String text) {
        if (text == null) {
            return "Missing mandatory option '--text'";
        }
        try {
            var newComment = new Comment(id, text);
            var savedComment = commentService.update(newComment);
            return "Updated comment: " + formatComment(savedComment);
        } catch (EntityNotFoundException e) {
            return "Error: Comment not found with id: " + id;
        }
    }

    @ShellMethod(value = "Delete a comment", key = {"dc", "delete-comment"})
    public String deleteComment(@ShellOption Long id) {
        commentService.delete(id);
        return "Comment deleted";
    }

    private String formatComment(Comment c) {
        return commentPrinter.print(c, null);
    }
}