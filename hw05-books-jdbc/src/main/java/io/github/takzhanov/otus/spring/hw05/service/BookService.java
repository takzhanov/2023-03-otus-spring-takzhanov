package io.github.takzhanov.otus.spring.hw05.service;

import io.github.takzhanov.otus.spring.hw05.domain.Book;
import io.github.takzhanov.otus.spring.hw05.service.dto.BookCreateRequest;
import io.github.takzhanov.otus.spring.hw05.service.dto.BookPatchRequest;
import io.github.takzhanov.otus.spring.hw05.service.dto.BookUpdateRequest;
import java.util.List;

public interface BookService {
    List<Book> findAll();

    Book findById(Long id);

    Book create(BookCreateRequest book);

    Book update(Book updatedBook);

    Book update(BookUpdateRequest updateRequest);

    Book patch(BookPatchRequest patchRequest);

    void delete(Long id);
}
