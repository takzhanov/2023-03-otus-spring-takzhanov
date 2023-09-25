package io.github.takzhanov.otus.spring.hw06orm.shell;

import io.github.takzhanov.otus.spring.hw06orm.domain.Author;
import io.github.takzhanov.otus.spring.hw06orm.domain.Genre;
import io.github.takzhanov.otus.spring.hw06orm.service.BookService;
import io.github.takzhanov.otus.spring.hw06orm.service.formatter.AuthorFormatterService;
import io.github.takzhanov.otus.spring.hw06orm.service.formatter.GenreFormatterService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class BookManagementCommands {
    private final BookService bookService;

    private final AuthorFormatterService authorFormatterService;

    private final GenreFormatterService genreFormatterService;

    @ShellMethod(value = "Add a comment to a book", key = {"ac", "add-comment"})
    public String addCommentToBook(@ShellOption(value = {"-i", "--id"}, help = "ID of the book") long id,
                                   @ShellOption(value = {"-c", "--comment"}, help = "Comment text") String text) {
        var addedComment = bookService.addCommentToBook(id, text);
        return "Added comment: " + addedComment;
    }

    @ShellMethod(value = "Add an author to a book", key = {"aa", "add-author"})
    public String addAuthorToBook(@ShellOption(value = {"-i", "--id"}, help = "ID of the book") long id,
                                  @ShellOption(value = {"-a", "--author"}, help = "Author ID") long authorId) {
        final Author author = bookService.addAuthorToBook(id, authorId);
        return "Added author: " + authorFormatterService.formatAuthor(author);
    }

    @ShellMethod(value = "Remove an author from a book", key = {"ra", "remove-author"})
    public String removeAuthorFromBook(@ShellOption(value = {"-i", "--id"}, help = "ID of the book") long id,
                                       @ShellOption(value = {"-a", "--authorId"}, help = "Author ID") long authorId) {
        final Author author = bookService.removeAuthor(id, authorId);
        return "Removed author: " + authorFormatterService.formatAuthor(author);
    }

    @ShellMethod(value = "Add a genre to a book", key = {"ag", "add-genre"})
    public String addGenreToBook(@ShellOption(value = {"-i", "--id"}, help = "ID of the book") long id,
                                 @ShellOption(value = {"-g", "--genre"}, help = "Genre ID") long genreId) {
        final Genre genre = bookService.addGenre(id, genreId);
        return "Added genre: " + genreFormatterService.formatGenre(genre);
    }

    @ShellMethod(value = "Remove a genre from a book", key = {"rg", "remove-genre"})
    public String removeGenreFromBook(@ShellOption(value = {"-i", "--id"}, help = "ID of the book") long id,
                                      @ShellOption(value = {"-g", "--genre"}, help = "Genre ID") long genreId) {
        final Genre genre = bookService.removeGenre(id, genreId);
        return "Removed genre: " + genreFormatterService.formatGenre(genre);
    }
}
