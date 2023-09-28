package io.github.takzhanov.otus.spring.hw02quiz.domain;

import java.util.List;

public record Question(String text, List<Answer> answers) {
}
