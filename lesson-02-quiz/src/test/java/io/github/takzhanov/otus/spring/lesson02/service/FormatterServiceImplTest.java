package io.github.takzhanov.otus.spring.lesson02.service;

import io.github.takzhanov.otus.spring.lesson02.domain.Answer;
import io.github.takzhanov.otus.spring.lesson02.domain.Question;
import io.github.takzhanov.otus.spring.lesson02.domain.User;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FormatterServiceImplTest {
    private final FormatterService formatterService = new FormatterServiceImpl();

    @Test
    void formatQuestions() {
        Question question1 = new Question("Question 1", List.of(new Answer("Answer 1", true)));
        Question question2 = new Question("Question 2", List.of(new Answer("Answer 2", true)));
        Question question3 = new Question("Question 3", List.of(new Answer("Answer 3", true)));
        List<Question> questions = Arrays.asList(question1, question2, question3);

        final String expected = """
                Q: Question 1
                A: Answer 1
                                
                Q: Question 2
                A: Answer 2
                                
                Q: Question 3
                A: Answer 3""";
        Assertions.assertEquals(expected, formatterService.formatQuestions(questions));
    }

    @Test
    void formatQuestion() {
        Question question1 = new Question("Question 1", List.of(new Answer("Answer 1", true)));
        final String expected = """
                Q: Question 1
                A: Answer 1""";
        Assertions.assertEquals(expected, formatterService.formatQuestion(question1));
    }

    @Test
    void formatUser() {
        Assertions.assertEquals("First Last", formatterService.formatUser(new User("First", "Last")));
    }

    @Test
    void formatScore() {
        Assertions.assertEquals("13 points", formatterService.formatScore(13));
    }
}