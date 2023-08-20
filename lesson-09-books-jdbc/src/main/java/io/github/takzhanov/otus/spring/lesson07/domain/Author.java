package io.github.takzhanov.otus.spring.lesson07.domain;

import lombok.Data;

@Data
public final class Author {
    private final Long id;

    private final String name;

    public Author(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Author(String name) {
        this(null, name);
    }
}
