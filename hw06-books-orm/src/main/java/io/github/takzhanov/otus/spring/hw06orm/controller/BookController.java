package io.github.takzhanov.otus.spring.hw06orm.controller;

import io.github.takzhanov.otus.spring.hw06orm.domain.Book;
import io.github.takzhanov.otus.spring.hw06orm.service.AuthorService;
import io.github.takzhanov.otus.spring.hw06orm.service.BookService;
import io.github.takzhanov.otus.spring.hw06orm.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    private final AuthorService authorService;

    private final CommentService commentService;

    @GetMapping("/books")
    public String listBooks(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "book/list";
    }

    @GetMapping("/books/{id}")
    public String viewBook(@PathVariable Long id, Model model) {
        Book book = bookService.getById(id);
        model.addAttribute("book", book);
        model.addAttribute("comments", bookService.getCommentsByBookId(id));
        model.addAttribute("allAuthors", authorService.findAll());
        return "book/view";
    }

    @GetMapping("/books/new")
    public String createBookForm(Model model) {
        model.addAttribute("book", new Book());
        return "book/form";
    }

    @PostMapping("/books")
    public String saveBook(@ModelAttribute("book") Book book) {
        bookService.createOrUpdateTitle(book);
        return "redirect:/books";
    }

    @GetMapping("/books/{id}/edit")
    public String editBookForm(@PathVariable Long id, Model model) {
        Book book = bookService.getById(id);
        model.addAttribute("book", book);
        return "book/form";
    }

    @PostMapping("/books/{id}/delete")
    public String deleteBook(@PathVariable Long id) {
        bookService.delete(id);
        return "redirect:/books";
    }

    @PostMapping("/books/{bookId}/comments")
    public String addComment(@PathVariable Long bookId, @RequestParam String text) {
        bookService.addCommentToBook(bookId, text);
        return "redirect:/books/" + bookId;
    }

    @PostMapping("/books/{bookId}/comments/{commentId}/delete")
    public String removeComment(@PathVariable Long bookId, @PathVariable Long commentId) {
        commentService.delete(commentId);
        return "redirect:/books/" + bookId;
    }

    @PostMapping("/books/{bookId}/authors")
    public String addAuthor(@PathVariable Long bookId, @RequestParam Long authorId) {
        bookService.addAuthorToBook(bookId, authorId);
        return "redirect:/books/" + bookId;
    }

    @PostMapping("/books/{bookId}/authors/{authorId}/delete")
    public String removeAuthor(@PathVariable Long bookId, @PathVariable Long authorId) {
        bookService.removeAuthorFromBook(bookId, authorId);
        return "redirect:/books/" + bookId;
    }

}
