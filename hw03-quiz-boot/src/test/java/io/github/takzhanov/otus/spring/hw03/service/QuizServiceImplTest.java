package io.github.takzhanov.otus.spring.hw03.service;

import io.github.takzhanov.otus.spring.hw03.config.LocalizationProperties;
import io.github.takzhanov.otus.spring.hw03.dao.QuestionRepository;
import io.github.takzhanov.otus.spring.hw03.domain.Answer;
import io.github.takzhanov.otus.spring.hw03.domain.Question;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.startsWith;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class QuizServiceImplTest {
    @MockBean
    private CommandLineRunner disabledClr;

    @MockBean
    private QuestionRepository mockQuestionRepository;

    @MockBean
    private IOService mockIOService;

    @MockBean
    private LocalizationProperties mockLocalizationProperties;

    @Autowired
    private InteractiveServiceImpl interactiveService;

    @BeforeEach
    void setUp() {
        when(mockLocalizationProperties.getLocale()).thenReturn(Locale.ENGLISH);
    }

    @Test
    void runQuiz() {
        var question1 = new Question("Question 1", List.of(new Answer("Answer 1", true)));
        var question2 = new Question("Question 2", List.of(new Answer("Answer 2", true)));
        var question3 = new Question("Question 3", List.of(new Answer("Answer 3", true)));
        var questions = Arrays.asList(question1, question2, question3);

        when(mockQuestionRepository.findAll()).thenReturn(questions);
        when(mockIOService.readLine()).thenReturn("Yury", "T", "Answer 1", "Answer 2", "Wrong", "no");

        interactiveService.runQuiz();

        verify(mockIOService, times(6)).readLine();
        verify(mockIOService).print(startsWith("Enter your first name:"));
        verify(mockIOService).print(startsWith("Enter your last name:"));
        verify(mockIOService).print(startsWith("Welcome to the quiz, Yury T!"));
        verify(mockIOService, times(3)).print(startsWith("Enter your answer: "));
        verify(mockIOService).print("Yury T, your score is: 2 points");
    }
}