package io.github.takzhanov.otus.spring.lesson07.service.dto;

public record BookPatchRequest(Long id, String title, String[] authorNames, String[] genreNames) {
}
