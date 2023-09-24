package io.github.takzhanov.otus.spring.hw06orm.shell;

import io.github.takzhanov.otus.spring.hw06orm.domain.Author;
import io.github.takzhanov.otus.spring.hw06orm.exception.ConstraintException;
import io.github.takzhanov.otus.spring.hw06orm.exception.EntityAlreadyExistsException;
import io.github.takzhanov.otus.spring.hw06orm.exception.EntityNotFoundException;
import io.github.takzhanov.otus.spring.hw06orm.service.formatter.AuthorFormatterService;
import io.github.takzhanov.otus.spring.hw06orm.service.AuthorService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class AuthorCommands {
    private final AuthorService authorService;

    private final AuthorFormatterService authorFormatterService;

    @ShellMethod(value = "List all authors", key = {"la", "list-authors"})
    public String listAuthors() {
        List<Author> authors = authorService.findAll();
        return authorFormatterService.formatAuthors(authors);
    }

    @ShellMethod(value = "Create a new author", key = {"ca", "create-author"})
    public String createAuthor(@ShellOption String name) {
        Author newAuthor = new Author(null, name);
        Author savedAuthor = authorService.create(newAuthor);
        return "Created new author: " + authorFormatterService.formatAuthor(savedAuthor);
    }

    @ShellMethod(value = "Update an author", key = {"ua", "update-author"})
    public String updateAuthor(@ShellOption Long id, @ShellOption String name) {
        if (name == null) {
            return "Missing mandatory option '--name'";
        }
        try {
            var newAuthor = new Author(id, name);
            var savedAuthor = authorService.update(newAuthor);
            return "Updated author: " + authorFormatterService.formatAuthor(savedAuthor);
        } catch (EntityNotFoundException e) {
            return "Error: Author not found with id: " + id;
        } catch (EntityAlreadyExistsException e) {
            return "Error: Author with name '%s' already exists".formatted(name);
        }
    }

    @ShellMethod(value = "Delete an author", key = {"da", "delete-author"})
    public String deleteAuthor(@ShellOption Long id, @ShellOption boolean force) {
        if (force) {
            authorService.forceDelete(id);
        } else {
            try {
                authorService.delete(id);
            } catch (ConstraintException e) {
                return "There are links. Try to use with --force option";
            }
        }
        return "Deleted author with id = " + id;
    }
}