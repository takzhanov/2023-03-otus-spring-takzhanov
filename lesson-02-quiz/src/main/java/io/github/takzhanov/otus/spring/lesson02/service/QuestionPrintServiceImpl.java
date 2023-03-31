package io.github.takzhanov.otus.spring.lesson02.service;

import io.github.takzhanov.otus.spring.lesson02.domain.Answer;
import io.github.takzhanov.otus.spring.lesson02.domain.Question;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuestionPrintServiceImpl implements QuestionPrintService {
    private final OutputService out;

    @Override
    public void printQuestions(List<Question> questions) {
        for (Question question : questions) {
            printQuestion(question);
            out.println();
        }
    }

    @Override
    public void printQuestion(Question question) {
        out.println("Q: " + question.text());
        for (Answer answer : question.answers()) {
            out.println("A: " + answer.text());
        }
    }
}

