package io.github.takzhanov.otus.spring.hw06orm.service;

import io.github.takzhanov.otus.spring.hw06orm.domain.Book;
import io.github.takzhanov.otus.spring.hw06orm.service.dto.BookCreateRequest;
import io.github.takzhanov.otus.spring.hw06orm.service.dto.BookPatchRequest;
import io.github.takzhanov.otus.spring.hw06orm.service.dto.BookUpdateRequest;
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
