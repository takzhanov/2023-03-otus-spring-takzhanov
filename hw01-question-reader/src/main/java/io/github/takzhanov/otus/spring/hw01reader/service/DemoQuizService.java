package io.github.takzhanov.otus.spring.hw01reader.service;

import io.github.takzhanov.otus.spring.hw01reader.dao.QuestionRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DemoQuizService implements QuizService {
    private final QuestionRepository questionRepository;

    private final QuestionPrintService questionPrintService;

    @Override
    public void runQuiz() {
        var questions = questionRepository.findAll();
        questionPrintService.printQuestions(questions);
    }
}
