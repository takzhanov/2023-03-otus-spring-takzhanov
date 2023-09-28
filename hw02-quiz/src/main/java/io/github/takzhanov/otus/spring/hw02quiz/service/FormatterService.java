package io.github.takzhanov.otus.spring.hw02quiz.service;

import io.github.takzhanov.otus.spring.hw02quiz.domain.Question;
import io.github.takzhanov.otus.spring.hw02quiz.domain.User;
import java.util.List;

public interface FormatterService {
    String formatQuestions(List<Question> questions);

    String formatQuestion(Question question);

    String formatUser(User user);

    String formatScore(long score);
}
