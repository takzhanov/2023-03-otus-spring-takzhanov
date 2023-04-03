package io.github.takzhanov.otus.spring.lesson01.dao;

import io.github.takzhanov.otus.spring.lesson01.domain.Question;
import java.util.List;

public interface QuestionRepository {
    List<Question> findAll();
}
