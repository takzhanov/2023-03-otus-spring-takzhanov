package io.github.takzhanov.otus.spring.lesson01.service;

import io.github.takzhanov.otus.spring.lesson01.domain.Answer;
import io.github.takzhanov.otus.spring.lesson01.domain.Question;

import java.util.List;

public class QuestionPrinterService {
    public void printQuestions(List<Question> questions) {
        for (Question question : questions) {
            System.out.println("Q: " + question.text());
            for (Answer answer : question.answers()) {
                System.out.println("A: " + (answer.correct() ? "*" : " ") + answer.text());
            }
            System.out.println();
        }
    }
}
