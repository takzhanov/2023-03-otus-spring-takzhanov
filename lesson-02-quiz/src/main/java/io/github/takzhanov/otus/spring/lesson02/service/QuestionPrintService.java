package io.github.takzhanov.otus.spring.lesson02.service;

import io.github.takzhanov.otus.spring.lesson02.domain.Question;
import java.util.List;

public interface QuestionPrintService {
    void printQuestions(List<Question> questions);

    void printQuestion(Question question);
}
