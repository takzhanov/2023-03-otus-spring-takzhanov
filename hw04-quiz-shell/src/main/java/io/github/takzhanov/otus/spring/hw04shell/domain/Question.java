package io.github.takzhanov.otus.spring.hw04shell.domain;

import java.util.List;

public record Question(String text, List<Answer> answers) {
}
