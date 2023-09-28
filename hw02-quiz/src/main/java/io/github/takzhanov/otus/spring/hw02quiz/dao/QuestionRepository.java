package io.github.takzhanov.otus.spring.hw02quiz.dao;

import io.github.takzhanov.otus.spring.hw02quiz.domain.Question;
import java.util.List;

public interface QuestionRepository {
    List<Question> findAll();
}
