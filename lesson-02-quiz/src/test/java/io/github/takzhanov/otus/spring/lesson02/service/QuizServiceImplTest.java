package io.github.takzhanov.otus.spring.lesson02.service;

import io.github.takzhanov.otus.spring.lesson02.dao.QuestionRepository;
import io.github.takzhanov.otus.spring.lesson02.domain.Answer;
import io.github.takzhanov.otus.spring.lesson02.domain.Question;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class QuizServiceImplTest {
    private QuestionRepository mockQuestionRepository;
    private FormatterService mockFormatterService;
    private IOService mockIOService;
    private QuizServiceImpl quizServiceImpl;

    @BeforeEach
    void setUp() {
        mockQuestionRepository = mock(QuestionRepository.class);
        mockFormatterService = new FormatterServiceImpl();
        mockIOService = mock(IOService.class);
        quizServiceImpl = new QuizServiceImpl(mockQuestionRepository, mockFormatterService, mockIOService);
    }

    @Test
    void runQuiz() {
        Question question1 = new Question("Question 1", List.of(new Answer("Answer 1", true)));
        Question question2 = new Question("Question 2", List.of(new Answer("Answer 2", true)));
        Question question3 = new Question("Question 3", List.of(new Answer("Answer 3", true)));
        List<Question> questions = Arrays.asList(question1, question2, question3);

        when(mockQuestionRepository.findAll()).thenReturn(questions);
        when(mockIOService.readLine()).thenReturn("Yury", "T", "Answer 1", "Answer 2", "Wrong", "no");

        quizServiceImpl.runQuiz();

        verify(mockIOService, times(6)).readLine();
        verify(mockIOService).println(startsWith("Welcome to the quiz, Yury T!"));
        verify(mockIOService, times(3)).println(startsWith("Enter your answer:"));
        verify(mockIOService).println("Yury T, your score is: 2 points");
    }
}