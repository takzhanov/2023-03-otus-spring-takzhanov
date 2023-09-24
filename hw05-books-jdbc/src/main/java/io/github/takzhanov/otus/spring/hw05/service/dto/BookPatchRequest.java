package io.github.takzhanov.otus.spring.hw05.service.dto;

public record BookPatchRequest(Long id, String title, String[] authorNames, String[] genreNames) {
}
