package io.github.takzhanov.otus.spring.lesson01.domain;

import java.util.List;

public record Question(String text, List<Answer> answers) {
}
