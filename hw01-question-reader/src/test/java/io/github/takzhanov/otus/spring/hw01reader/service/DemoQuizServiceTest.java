package io.github.takzhanov.otus.spring.hw01reader.service;

import io.github.takzhanov.otus.spring.hw01reader.dao.QuestionRepository;
import io.github.takzhanov.otus.spring.hw01reader.domain.Question;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.only;

class DemoQuizServiceTest {
    private QuestionRepository mockQuestionRepository;
    private QuestionPrintService mockQuestionPrintService;
    private DemoQuizService demoQuiz;

    @BeforeEach
    void setUp() {
        mockQuestionRepository = Mockito.mock(QuestionRepository.class);
        mockQuestionPrintService = Mockito.mock(QuestionPrintService.class);
        demoQuiz = new DemoQuizService(mockQuestionRepository, mockQuestionPrintService);
    }

    @Test
    void runQuiz() {
        List<Question> fakeQuestions = new ArrayList<>();
        Mockito.when(mockQuestionRepository.findAll()).thenReturn(fakeQuestions);

        demoQuiz.runQuiz();

        Mockito.verify(mockQuestionRepository, only()).findAll();
        Mockito.verify(mockQuestionPrintService, only()).printQuestions(fakeQuestions);
    }
}