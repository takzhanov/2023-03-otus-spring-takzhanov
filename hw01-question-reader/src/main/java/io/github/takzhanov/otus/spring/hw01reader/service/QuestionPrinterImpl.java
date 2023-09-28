package io.github.takzhanov.otus.spring.hw01reader.service;

import io.github.takzhanov.otus.spring.hw01reader.domain.Answer;
import io.github.takzhanov.otus.spring.hw01reader.domain.Question;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QuestionPrinterImpl implements QuestionPrintService {
    private final OutputService out;

    @Override
    public void printQuestions(List<Question> questions) {
        for (Question question : questions) {
            out.println("Q: " + question.text());
            for (Answer answer : question.answers()) {
                out.println("A: " + (answer.correct() ? "*" : " ") + answer.text());
            }
            out.println();
        }
    }
}
