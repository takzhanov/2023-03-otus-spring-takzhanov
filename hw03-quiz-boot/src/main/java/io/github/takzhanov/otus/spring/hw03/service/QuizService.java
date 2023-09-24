package io.github.takzhanov.otus.spring.hw03.service;

import io.github.takzhanov.otus.spring.hw03.domain.Question;
import io.github.takzhanov.otus.spring.hw03.domain.Score;
import io.github.takzhanov.otus.spring.hw03.domain.UserResult;
import java.util.List;

public interface QuizService {
    List<Question> prepareQuestion();

    Score processResult(UserResult userResult);
}
