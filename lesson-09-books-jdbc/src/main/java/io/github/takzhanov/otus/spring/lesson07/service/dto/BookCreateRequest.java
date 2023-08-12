package io.github.takzhanov.otus.spring.lesson07.service.dto;

public record BookCreateRequest(String title, String[] authorNames, String[] genreNames) {
}
