package io.github.takzhanov.otus.spring.hw06orm.shell;

import io.github.takzhanov.otus.spring.hw06orm.service.BookService;
import io.github.takzhanov.otus.spring.hw06orm.service.formatter.FormatterService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class BookManagementCommands {
    private final BookService bookService;

    private final FormatterService formatterService;

    @ShellMethod(value = "Show comments to a book", key = {"sc", "show-comments"})
    public String showCommentsToBook(@ShellOption(value = {"-i", "--id"}, help = "ID of the book") long bookId) {

        var book = bookService.getById(bookId);
        var comments = bookService.getCommentsByBookId(bookId);

        return "Comments for [%s]: \n%s".formatted(
                book.getTitle(),
                formatterService.format(comments));
    }

    @ShellMethod(value = "Add a comment to a book", key = {"ac", "add-comment"})
    public String addCommentToBook(@ShellOption(value = {"-i", "--id"}, help = "ID of the book") long bookId,
                                   @ShellOption(value = {"-c", "--comment"}, help = "Comment text") String text) {

        //TODO кажется ID должен проставляться, ан нет...
        var addedComment = bookService.addCommentToBook(bookId, text);
        return "Added comment: " + formatterService.format(addedComment);
    }

    @ShellMethod(value = "Add an author to a book", key = {"aa", "add-author"})
    public String addAuthorToBook(@ShellOption(value = {"-i", "--id"}, help = "ID of the book") long bookId,
                                  @ShellOption(value = {"-a", "--author"}, help = "Author ID") long authorId) {

        var author = bookService.addAuthorToBook(bookId, authorId);
        return "Added author: " + formatterService.format(author);
    }

    @ShellMethod(value = "Remove an author from a book", key = {"ra", "remove-author"})
    public String removeAuthorFromBook(@ShellOption(value = {"-i", "--id"}, help = "ID of the book") long bookId,
                                       @ShellOption(value = {"-a", "--authorId"}, help = "Author ID") long authorId) {

        var author = bookService.removeAuthorFromBook(bookId, authorId);
        return "Removed author: " + formatterService.format(author);
    }

    @ShellMethod(value = "Add a genre to a book", key = {"ag", "add-genre"})
    public String addGenreToBook(@ShellOption(value = {"-i", "--bookId"}, help = "ID of the book") long bookId,
                                 @ShellOption(value = {"-g", "--genre"}, help = "Genre ID") long genreId) {

        var genre = bookService.addGenreToBook(bookId, genreId);
        return "Added genre: " + formatterService.format(genre);
    }

    @ShellMethod(value = "Remove a genre from a book", key = {"rg", "remove-genre"})
    public String removeGenreFromBook(@ShellOption(value = {"-i", "--id"}, help = "ID of the book") long bookId,
                                      @ShellOption(value = {"-g", "--genre"}, help = "Genre ID") long genreId) {

        var genre = bookService.removeGenreFromBook(bookId, genreId);
        return "Removed genre: " + formatterService.format(genre);
    }
}
