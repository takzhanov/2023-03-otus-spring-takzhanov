package io.github.takzhanov.otus.spring.lesson07.service.dto;

public record BookUpdateRequest(Long id, String title, String[] authorNames, String[] genreNames) {
}
