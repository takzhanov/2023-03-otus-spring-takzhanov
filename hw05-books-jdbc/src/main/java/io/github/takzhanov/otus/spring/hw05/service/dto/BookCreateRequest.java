package io.github.takzhanov.otus.spring.hw05.service.dto;

public record BookCreateRequest(String title, String[] authorNames, String[] genreNames) {
}
