package io.github.takzhanov.otus.spring.hw03.dao;

import io.github.takzhanov.otus.spring.hw03.domain.Question;
import java.util.List;

public interface QuestionRepository {
    List<Question> findAll();
}
