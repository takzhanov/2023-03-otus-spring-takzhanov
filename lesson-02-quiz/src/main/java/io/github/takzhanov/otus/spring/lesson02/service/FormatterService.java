package io.github.takzhanov.otus.spring.lesson02.service;

import io.github.takzhanov.otus.spring.lesson02.domain.Question;
import io.github.takzhanov.otus.spring.lesson02.domain.User;
import java.util.List;

public interface FormatterService {
    String formatQuestions(List<Question> questions);

    String formatQuestion(Question question);

    String formatUser(User user);

    String formatScore(long score);
}
