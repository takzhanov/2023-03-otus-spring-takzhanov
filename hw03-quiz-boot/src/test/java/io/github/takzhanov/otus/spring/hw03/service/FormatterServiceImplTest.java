package io.github.takzhanov.otus.spring.hw03.service;

import io.github.takzhanov.otus.spring.hw03.config.AppProperties;
import io.github.takzhanov.otus.spring.hw03.domain.Answer;
import io.github.takzhanov.otus.spring.hw03.domain.Question;
import io.github.takzhanov.otus.spring.hw03.domain.User;
import io.github.takzhanov.otus.spring.hw03.service.converter.ScoreConverter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;

@SpringBootTest
class FormatterServiceImplTest {
    @MockBean
    private CommandLineRunner disabledClr;

    @MockBean
    private AppProperties mockLocalizationProperties;

    @Autowired
    private FormatterService formatterService;

    @Autowired
    private ScoreConverter scoreConverter;

    @BeforeEach
    void setUp() {
        when(mockLocalizationProperties.getLocale()).thenReturn(Locale.ENGLISH);
    }

    @Test
    void formatQuestions() {
        var question1 = new Question("Question 1", List.of(new Answer("Answer 1", true)));
        var question2 = new Question("Question 2", List.of(new Answer("Answer 2", true)));
        var question3 = new Question("Question 3", List.of(new Answer("Answer 3", true)));
        var questions = Arrays.asList(question1, question2, question3);

        val expected = """
                Q: Question 1
                A: Answer 1
                                
                Q: Question 2
                A: Answer 2
                                
                Q: Question 3
                A: Answer 3""";
        Assertions.assertEquals(expected, formatterService.format(questions));
    }

    @Test
    void formatQuestion() {
        var question1 = new Question("Question 1", List.of(new Answer("Answer 1", true)));
        val expected = """
                Q: Question 1
                A: Answer 1""";
        Assertions.assertEquals(expected, formatterService.format(question1));
    }

    @Test
    void formatUser() {
        Assertions.assertEquals("First Last", formatterService.format(new User("First", "Last")));
    }

    @Test
    void formatScore() {
        Assertions.assertEquals("13 points", formatterService.format(13L));
    }
}