package io.github.takzhanov.otus.spring.hw06orm.shell;

import io.github.takzhanov.otus.spring.hw06orm.service.BookService;
import io.github.takzhanov.otus.spring.hw06orm.service.dto.BookCreateRequest;
import io.github.takzhanov.otus.spring.hw06orm.service.dto.BookPatchRequest;
import io.github.takzhanov.otus.spring.hw06orm.service.dto.BookUpdateRequest;
import io.github.takzhanov.otus.spring.hw06orm.service.formatter.FormatterService;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class BookCommands {
    private final BookService bookService;

    private final FormatterService formatterService;

    @ShellMethod(value = "List all books | Find book", key = {"lb", "list-books"})
    public String listBooks(@ShellOption(defaultValue = "") Long id) {
        if (id == null) {
            var books = bookService.findAll();
            return formatterService.format(books);
        } else {
            var book = bookService.getById(id);
            return formatterService.format(book);
        }
    }

    @ShellMethod(value = "Create a new book", key = {"cb", "create-book"})
    public String createBook(@ShellOption({"-t", "--title"}) String title,
                             @ShellOption(value = {"-a", "--authors"},
                                     defaultValue = ShellOption.NULL) String[] authors,
                             @ShellOption(value = {"-g", "--genres"},
                                     defaultValue = ShellOption.NULL) String[] genres) {

        var bookCreateRequest = new BookCreateRequest(title, authors, genres);
        var savedBook = bookService.create(bookCreateRequest);
        return "Created book: \n" + formatterService.format(savedBook);
    }

    @ShellMethod(value = "Update a book", key = {"ub", "update-book"})
    public String updateBook(@ShellOption(value = {"-i", "--id"}, help = "ID of the book") long id,
                             @ShellOption(value = {"-t", "--title"}, defaultValue = ShellOption.NULL,
                                     help = "New title of the book") String title,
                             @ShellOption(value = {"-a", "--authors"}, defaultValue = ShellOption.NULL,
                                     help = "New authors of the book, separated by commas") String[] authorNames,
                             @ShellOption(value = {"-g", "--genres"}, defaultValue = ShellOption.NULL,
                                     help = "New genres of the book, separated by commas") String[] genreNames) {

        if (title == null) {
            return "Missing mandatory option '--title'";
        }
        var bookUpdateRequest = new BookUpdateRequest(id, title, authorNames, genreNames);
        var updatedBook = bookService.update(bookUpdateRequest);
        return "Updated book: \n" + formatterService.format(updatedBook);
    }

    @ShellMethod(value = "Patch a book", key = {"pb", "patch-book"})
    public String patchBook(@ShellOption(value = {"-i", "--id"}, help = "ID of the book") long id,
                            @ShellOption(value = {"-t", "--title"}, defaultValue = ShellOption.NULL,
                                    help = "New title of the book") String title,
                            @ShellOption(value = {"-a", "--authors"}, defaultValue = ShellOption.NULL,
                                    help = "New authors of the book, separated by commas") String[] authorNames,
                            @ShellOption(value = {"-g", "--genres"}, defaultValue = ShellOption.NULL,
                                    help = "New genres of the book, separated by commas") String[] genreNames) {

        var bookPatchRequest = new BookPatchRequest(id, title, authorNames, genreNames);
        var patchedBook = bookService.patch(bookPatchRequest);
        return "Patched book: \n" + formatterService.format(patchedBook);
    }

    @ShellMethod(value = "Delete a book", key = {"db", "delete-book"})
    public String deleteBook(@ShellOption Long id) {
        bookService.delete(id);
        return "Book deleted";
    }
}
