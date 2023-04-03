package io.github.takzhanov.otus.spring.lesson01.service;

import io.github.takzhanov.otus.spring.lesson01.dao.QuestionRepository;
import io.github.takzhanov.otus.spring.lesson01.domain.Question;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.only;

class DemoQuizTest {
    private QuestionRepository mockQuestionRepository;
    private QuestionPrinter mockQuestionPrinter;
    private DemoQuiz demoQuiz;

    @BeforeEach
    void setUp() {
        mockQuestionRepository = Mockito.mock(QuestionRepository.class);
        mockQuestionPrinter = Mockito.mock(QuestionPrinter.class);
        demoQuiz = new DemoQuiz(mockQuestionRepository, mockQuestionPrinter);
    }

    @Test
    void runQuiz() {
        List<Question> fakeQuestions = new ArrayList<>();
        Mockito.when(mockQuestionRepository.findAll()).thenReturn(fakeQuestions);

        demoQuiz.runQuiz();

        Mockito.verify(mockQuestionRepository, only()).findAll();
        Mockito.verify(mockQuestionPrinter, only()).printQuestions(fakeQuestions);
    }
}