package io.github.takzhanov.otus.spring.lesson07.shell;

import io.github.takzhanov.otus.spring.lesson07.domain.Genre;
import io.github.takzhanov.otus.spring.lesson07.exception.ConstraintException;
import io.github.takzhanov.otus.spring.lesson07.exception.EntityAlreadyExistsException;
import io.github.takzhanov.otus.spring.lesson07.exception.EntityNotFoundException;
import io.github.takzhanov.otus.spring.lesson07.service.GenreFormatterService;
import io.github.takzhanov.otus.spring.lesson07.service.GenreService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class GenreCommands {
    private final GenreService genreService;

    private final GenreFormatterService genreFormatterService;

    @ShellMethod(value = "List all genres", key = {"lg", "list-genres"})
    public String listGenres() {
        List<Genre> genres = genreService.findAll();
        return genreFormatterService.formatGenres(genres);
    }

    @ShellMethod(value = "Create a new genre", key = {"cg", "create-genre"})
    public String createGenre(@ShellOption String name) {
        Genre newGenre = new Genre(null, name);
        Genre savedGenre = genreService.create(newGenre);
        return "Created new genre: " + genreFormatterService.formatGenre(savedGenre);
    }

    @ShellMethod(value = "Update a genre", key = {"ug", "update-genre"})
    public String updateGenre(@ShellOption Long id, @ShellOption String name) {
        if (name == null) {
            return "Missing mandatory option '--name'";
        }
        try {
            var newGenre = new Genre(id, name);
            var savedGenre = genreService.update(newGenre);
            return "Updated genre: " + genreFormatterService.formatGenre(savedGenre);
        } catch (EntityNotFoundException e) {
            return "Error: Genre not found with id: " + id;
        } catch (EntityAlreadyExistsException e) {
            return "Error: Genre with name '%s' already exists".formatted(name);
        }
    }

    @ShellMethod(value = "Delete a genre", key = {"dg", "delete-genre"})
    public String deleteGenre(@ShellOption Long id, @ShellOption boolean force) {
        if (force) {
            genreService.forceDelete(id);
        } else {
            try {
                genreService.delete(id);
            } catch (ConstraintException e) {
                return "There are links. Try to use with --force option";
            }
        }
        return "Deleted genre with id = " + id;
    }
}

