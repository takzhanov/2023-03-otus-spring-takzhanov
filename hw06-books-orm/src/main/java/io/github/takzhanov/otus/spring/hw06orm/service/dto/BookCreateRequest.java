package io.github.takzhanov.otus.spring.hw06orm.service.dto;

public record BookCreateRequest(String title, String[] authorNames, String[] genreNames) {
}
