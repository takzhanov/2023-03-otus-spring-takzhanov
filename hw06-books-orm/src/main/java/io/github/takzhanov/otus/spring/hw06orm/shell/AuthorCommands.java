package io.github.takzhanov.otus.spring.hw06orm.shell;

import io.github.takzhanov.otus.spring.hw06orm.domain.Author;
import io.github.takzhanov.otus.spring.hw06orm.exception.ConstraintException;
import io.github.takzhanov.otus.spring.hw06orm.service.AuthorService;
import io.github.takzhanov.otus.spring.hw06orm.service.formatter.FormatterService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class AuthorCommands {
    private final AuthorService authorService;

    private final FormatterService formatterService;

    @ShellMethod(value = "List all authors", key = {"la", "list-authors"})
    public String listAuthors() {
        var authors = authorService.findAll();
        return formatterService.format(authors);
    }

    @ShellMethod(value = "Create a new author", key = {"ca", "create-author"})
    public String createAuthor(@ShellOption String name) {
        Author newAuthor = new Author(null, name);
        Author savedAuthor = authorService.create(newAuthor);
        return "Created author: " + formatterService.format(savedAuthor);
    }

    @ShellMethod(value = "Update an author", key = {"ua", "update-author"})
    public String updateAuthor(@ShellOption Long id, @ShellOption String name) {
        if (name == null) {
            return "Missing mandatory option '--name'";
        }
        try {
            var newAuthor = new Author(id, name);
            var savedAuthor = authorService.update(newAuthor);
            return "Updated author: " + formatterService.format(savedAuthor);
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintException("Author with name '%s' already exists".formatted(name));
        }
    }

    @ShellMethod(value = "Delete an author", key = {"da", "delete-author"})
    public String deleteAuthor(@ShellOption Long id) {
        try {
            authorService.delete(id);
        } catch (DataIntegrityViolationException e) {
            throw new ConstraintException("There are links. You cannot delete this author");
        }
        return "Author deleted";
    }
}