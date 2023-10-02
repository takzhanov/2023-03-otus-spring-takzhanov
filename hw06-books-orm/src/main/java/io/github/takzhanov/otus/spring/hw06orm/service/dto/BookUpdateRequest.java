package io.github.takzhanov.otus.spring.hw06orm.service.dto;

public record BookUpdateRequest(Long id, String title, String[] authorNames, String[] genreNames) {
}
