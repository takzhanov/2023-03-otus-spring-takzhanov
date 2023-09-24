package io.github.takzhanov.otus.spring.hw06orm.shell;

import io.github.takzhanov.otus.spring.hw06orm.domain.Book;
import io.github.takzhanov.otus.spring.hw06orm.exception.EntityNotFoundException;
import io.github.takzhanov.otus.spring.hw06orm.service.BookFormatterService;
import io.github.takzhanov.otus.spring.hw06orm.service.BookService;
import io.github.takzhanov.otus.spring.hw06orm.service.dto.BookCreateRequest;
import io.github.takzhanov.otus.spring.hw06orm.service.dto.BookPatchRequest;
import io.github.takzhanov.otus.spring.hw06orm.service.dto.BookUpdateRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
@RequiredArgsConstructor
public class BookCommands {
    private final BookService bookService;

    private final BookFormatterService bookFormatterService;

    @ShellMethod(value = "List all books", key = {"l", "lb", "list-books"})
    public String listBooks() {
        List<Book> books = bookService.findAll();
        return bookFormatterService.formatBooks(books);
    }

    @ShellMethod(value = "Create a new book", key = {"c", "cb", "create-book"})
    public String createBook(@ShellOption({"-t", "--title"}) String title,
                             @ShellOption(value = {"-a", "--authors"},
                                     defaultValue = ShellOption.NULL) String[] authors,
                             @ShellOption(value = {"-g", "--genres"},
                                     defaultValue = ShellOption.NULL) String[] genres) {

        var bookCreateRequest = new BookCreateRequest(title, authors, genres);
        var savedBook = bookService.create(bookCreateRequest);
        return "Created book: \n" + bookFormatterService.formatBook(savedBook);
    }

    @ShellMethod(value = "Read a book", key = {"r", "rb", "read-book"})
    public String readBook(@ShellOption Long id) {
        var foundBook = bookService.findById(id);
        if (foundBook == null) {
            return "Book not found with id: " + id;
        }
        return "Book with id = " + id + ":\n" + bookFormatterService.formatBook(foundBook);
    }

    @ShellMethod(value = "Update a book", key = {"u", "ub", "update-book"})
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
        try {
            var bookUpdateRequest = new BookUpdateRequest(id, title, authorNames, genreNames);
            var updatedBook = bookService.update(bookUpdateRequest);
            return "Updated book: \n" + bookFormatterService.formatBook(updatedBook);
        } catch (EntityNotFoundException e) {
            return "Error: Book not found with id: " + id;
        }
    }

    @ShellMethod(value = "Patch a book", key = {"p", "pb", "patch-book"})
    public String patchBook(@ShellOption(value = {"-i", "--id"}, help = "ID of the book") long id,
                            @ShellOption(value = {"-t", "--title"}, defaultValue = ShellOption.NULL,
                                    help = "New title of the book") String title,
                            @ShellOption(value = {"-a", "--authors"}, defaultValue = ShellOption.NULL,
                                    help = "New authors of the book, separated by commas") String[] authorNames,
                            @ShellOption(value = {"-g", "--genres"}, defaultValue = ShellOption.NULL,
                                    help = "New genres of the book, separated by commas") String[] genreNames) {

        try {
            var bookPatchRequest = new BookPatchRequest(id, title, authorNames, genreNames);
            var patchedBook = bookService.patch(bookPatchRequest);
            return "Patched book: \n" + bookFormatterService.formatBook(patchedBook);
        } catch (EntityNotFoundException e) {
            return "Error: Book not found with id: " + id;
        }
    }

    @ShellMethod(value = "Delete a book", key = {"d", "db", "delete-book"})
    public String deleteBook(@ShellOption Long id) {
        bookService.delete(id);
        return "Deleted book with id = " + id;
    }
}
