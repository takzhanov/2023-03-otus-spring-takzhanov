package io.github.takzhanov.otus.spring.hw03.service;

import io.github.takzhanov.otus.spring.hw03.domain.Question;
import io.github.takzhanov.otus.spring.hw03.domain.User;
import java.util.List;

public interface FormatterService {
    String formatQuestions(List<Question> questions);

    String formatQuestion(Question question);

    String formatUser(User user);

    String formatScore(long score);
}
