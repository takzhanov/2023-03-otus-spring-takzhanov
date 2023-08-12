package io.github.takzhanov.otus.spring.hw03.domain;

import java.util.List;

public record Question(String text, List<Answer> answers) {
}
