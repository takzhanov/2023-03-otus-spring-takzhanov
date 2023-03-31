package io.github.takzhanov.otus.spring.lesson02.dao;

import io.github.takzhanov.otus.spring.lesson02.domain.Question;
import java.util.List;

public interface QuestionRepository {
    List<Question> findAll();
}
