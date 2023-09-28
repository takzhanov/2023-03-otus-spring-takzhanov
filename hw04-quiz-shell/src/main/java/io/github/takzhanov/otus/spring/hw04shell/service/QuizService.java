package io.github.takzhanov.otus.spring.hw04shell.service;

import io.github.takzhanov.otus.spring.hw04shell.domain.Question;
import io.github.takzhanov.otus.spring.hw04shell.domain.Score;
import io.github.takzhanov.otus.spring.hw04shell.domain.UserResult;
import java.util.List;

public interface QuizService {
    List<Question> prepareQuestion();

    Score processResult(UserResult userResult);
}
