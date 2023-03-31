package io.github.takzhanov.otus.spring.lesson02.service;

import io.github.takzhanov.otus.spring.lesson02.dao.QuestionRepository;
import io.github.takzhanov.otus.spring.lesson02.domain.Answer;
import io.github.takzhanov.otus.spring.lesson02.domain.Question;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class DemoQuizServiceTest {
    private QuestionRepository mockQuestionRepository;
    private QuestionPrintService mockQuestionPrintService;
    private InputService mockInputService;
    private OutputService mockOutputService;
    private DemoQuizService demoQuizService;

    @BeforeEach
    void setUp() {
        mockQuestionRepository = mock(QuestionRepository.class);
        mockQuestionPrintService = mock(QuestionPrintService.class);
        mockInputService = mock(InputService.class);
        mockOutputService = mock(OutputService.class);
        demoQuizService = new DemoQuizService(mockQuestionRepository, mockQuestionPrintService, mockInputService, mockOutputService);
    }

    @Test
    void runQuiz() {
        Question question1 = new Question("Question 1", List.of(new Answer("Answer 1", true)));
        Question question2 = new Question("Question 2", List.of(new Answer("Answer 2", true)));
        Question question3 = new Question("Question 3", List.of(new Answer("Answer 3", true)));
        List<Question> questions = Arrays.asList(question1, question2, question3);

        when(mockQuestionRepository.findAll()).thenReturn(questions);
        when(mockInputService.readLine()).thenReturn("Yury", "Answer 1", "Answer 2", "Wrong", "no");

        demoQuizService.runQuiz();

        verify(mockInputService, times(5)).readLine();
        verify(mockOutputService).println(startsWith("Welcome to the quiz, Yury!"));
        verify(mockOutputService, times(3)).println(startsWith("Enter your answer:"));
        verify(mockOutputService).println("Your score is: 2");
        verify(mockQuestionPrintService, times(3)).printQuestion(any(Question.class));
    }
}