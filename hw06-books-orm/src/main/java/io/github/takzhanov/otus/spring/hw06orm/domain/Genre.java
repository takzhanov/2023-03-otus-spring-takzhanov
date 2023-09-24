package io.github.takzhanov.otus.spring.hw06orm.domain;

import lombok.Data;

@Data
public final class Genre {
    private final Long id;

    private final String name;

    public Genre(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Genre(String name) {
        this(null, name);
    }
}
