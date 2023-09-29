package io.github.takzhanov.otus.spring.hw06orm.controller;

import io.github.takzhanov.otus.spring.hw06orm.domain.Book;
import io.github.takzhanov.otus.spring.hw06orm.service.AuthorService;
import io.github.takzhanov.otus.spring.hw06orm.service.BookService;
import io.github.takzhanov.otus.spring.hw06orm.service.CommentService;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private CommentService commentService;

    @Test
    void shouldReturnBookListView() throws Exception {
        when(bookService.findAll()).thenReturn(Collections.singletonList(new Book(1L, "Test Book")));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/list"))
                .andExpect(model().attributeExists("books"))
                .andExpect(model().attribute("books", Collections.singletonList(new Book(1L, "Test Book"))));
    }

    @Test
    void shouldReturnBookView() throws Exception {
        Book book = new Book(1L, "Test Book");
        when(bookService.getById(1L)).thenReturn(book);
        when(bookService.getCommentsByBookId(1L)).thenReturn(Collections.emptyList());
        when(authorService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/view"))
                .andExpect(model().attributeExists("book", "comments", "allAuthors"))
                .andExpect(model().attribute("book", book));
    }

    @Test
    void shouldReturnFormView() throws Exception {
        mockMvc.perform(get("/books/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/form"))
                .andExpect(model().attributeExists("book"));
    }

    @Test
    void shouldRedirectAfterSave() throws Exception {
        mockMvc.perform(post("/books").flashAttr("book", new Book()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
    }

    @Test
    void shouldReturnEditFormView() throws Exception {
        Book book = new Book(1L, "Test Book");
        when(bookService.getById(1L)).thenReturn(book);

        mockMvc.perform(get("/books/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/form"))
                .andExpect(model().attributeExists("book"))
                .andExpect(model().attribute("book", book));
    }

    @Test
    void shouldRedirectAfterDelete() throws Exception {
        mockMvc.perform(post("/books/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/books"));
    }

}

