package io.github.takzhanov.otus.spring.hw01reader.service;

import io.github.takzhanov.otus.spring.hw01reader.domain.Question;
import java.util.List;

public interface QuestionPrintService {
    void printQuestions(List<Question> questions);
}
