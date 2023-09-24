package io.github.takzhanov.otus.spring.hw06orm.domain;

import java.util.Set;
import lombok.Data;

@Data
public final class Book {
    private final Long id;

    private final String title;

    private final Set<Author> authors;

    private final Set<Genre> genres;
}