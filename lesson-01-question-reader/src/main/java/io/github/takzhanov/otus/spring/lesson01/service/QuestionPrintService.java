package io.github.takzhanov.otus.spring.lesson01.service;

import io.github.takzhanov.otus.spring.lesson01.domain.Question;
import java.util.List;

public interface QuestionPrintService {
    void printQuestions(List<Question> questions);
}
