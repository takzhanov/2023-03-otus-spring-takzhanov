package io.github.takzhanov.otus.spring.hw01reader.dao;

import io.github.takzhanov.otus.spring.hw01reader.domain.Question;
import java.util.List;

public interface QuestionRepository {
    List<Question> findAll();
}
