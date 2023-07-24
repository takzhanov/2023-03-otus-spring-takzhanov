package io.github.takzhanov.otus.spring.lesson02.domain;

public record User(String firstName, String lastName) {
    @Override
    public String toString() {
        return "%s %s".formatted(firstName, lastName);
    }
}
