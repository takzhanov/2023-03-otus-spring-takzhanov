package io.github.takzhanov.otus.spring.lesson01.service;

import io.github.takzhanov.otus.spring.lesson01.dao.QuestionRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DemoQuiz implements Quiz {
    private final QuestionRepository questionRepository;

    private final QuestionPrinter questionPrinter;

    @Override
    public void runQuiz() {
        questionPrinter.printQuestions(questionRepository.findAll());
    }
}
