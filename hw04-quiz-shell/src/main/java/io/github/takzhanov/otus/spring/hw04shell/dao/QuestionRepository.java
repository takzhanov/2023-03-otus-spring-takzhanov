package io.github.takzhanov.otus.spring.hw04shell.dao;

import io.github.takzhanov.otus.spring.hw04shell.domain.Question;
import java.util.List;

public interface QuestionRepository {
    List<Question> findAll();
}
